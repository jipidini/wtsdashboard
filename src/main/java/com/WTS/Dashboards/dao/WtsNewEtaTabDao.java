package com.WTS.Dashboards.dao;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.WTS.Dashboards.Entity.WtsAppMappingTab;
import com.WTS.Dashboards.Entity.WtsAppTab;
import com.WTS.Dashboards.Entity.WtsNewEtaTab;
import com.WTS.Dashboards.Entity.WtsProcessAppMapTab;
import com.WTS.Dashboards.Entity.WtsProcessTab;
import com.WTS.Dashboards.Entity.WtsTransTab;
import com.WTS.Dashboards.Service.EmailService;
import com.WTS.Dashboards.Utility.FileCreationTime;
import com.WTS.Dashboards.Utility.TreatmentDate;

@Transactional
@Repository
public class WtsNewEtaTabDao implements IWtsDaoInterface {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private WtsAppTabDao appDAO;

	@Autowired
	private WtsProcessTabDao processDAO;

	@Autowired
	private EmailService emailService;

	@Autowired
	private WtsProcessAppMapTabDao proAppMapDao;

	@Autowired
	private WtsAppMappingTabDao appMapDao;

	public WtsNewEtaTab getEtaById(int newEtaId) {
		return entityManager.find(WtsNewEtaTab.class, newEtaId);
	}

	public void addNewEta(WtsNewEtaTab newEta) {

		entityManager.persist(newEta);
		entityManager.flush();

	}

	public boolean isETAExistsForProcessAndApp(int processId, int appId) {
		String hql = "FROM WtsNewEtaTab where eventDate=? and processId=? and applicationId=?";
		List<WtsNewEtaTab> ls = (List<WtsNewEtaTab>) entityManager.createQuery(hql)
				.setParameter(1, TreatmentDate.getInstance().getTreatmentDate()).setParameter(2, processId)
				.setParameter(3, appId).getResultList();
		if (!ls.isEmpty())
			return true;
		else
			return false;
	}

	public boolean isETAExistsForParentChild(int processId, int parentId, int childId) {
		String hql = "FROM WtsNewEtaTab where eventDate=? and processId=? and parentId=? and childId=?";
		List<WtsNewEtaTab> ls = (List<WtsNewEtaTab>) entityManager.createQuery(hql)
				.setParameter(1, TreatmentDate.getInstance().getTreatmentDate()).setParameter(2, processId)
				.setParameter(3, parentId).setParameter(4, childId).getResultList();
		if (!ls.isEmpty())
			return true;
		else
			return false;
	}

	public void updateNewEta(WtsNewEtaTab newEta) {
		WtsNewEtaTab eta = getEtaById(newEta.getEtaId());
		eta.setApplicationId(newEta.getApplicationId());
		eta.setProcessId(newEta.getProcessId());
		eta.setNewEtaStartTransaction(newEta.getNewEtaStartTransaction());
		eta.setNewEtaEndTransaction(newEta.getNewEtaEndTransaction());
		eta.setEventDate(TreatmentDate.getInstance().getTreatmentDate());

	}

	public void deleteNewEta(int newEtaId) {
		entityManager.remove(getEtaById(newEtaId));

	}

	public int isProblemFlag() {
		int flag = 0;
		String hql = "select problemFlag FROM WtsNewEtaTab where eventDate= current_date";
		int entry = entityManager.createQuery(hql).getResultList().size();
		if (entry != 0) {
			flag = 0;
		} else {
			flag = 1;
		}
		return flag;
	}

	public void newEtaCalculation(WtsAppTab app, int processId) throws ParseException {
		System.out.println("start time loop entered");
		String name = app.getName();
		int apId = app.getApplicationId();

		List<WtsNewEtaTab> etaLst = new ArrayList<>();
		Timestamp startDTTime = null;
		Timestamp endDTTime = null;
		int buf = 0;
		int currentSeq = 0;
		WtsProcessAppMapTab proMap = proAppMapDao.getAllAppMappingsByProcess(processId, apId);
		if (proMap != null) {
			startDTTime = proMap.getStartTime();
			endDTTime = proMap.getEndTime();
			buf = proMap.getBufferTime();
			currentSeq = proMap.getSequence();
		}

		Long origDiff = Long.valueOf(0);
		origDiff = endDTTime.getTime() - startDTTime.getTime();

		Timestamp fileStart = null;
		Timestamp fileEnd = null;
		Timestamp lastEnd = null;
		Long endDiff = Long.valueOf(0);
		Long startDiff = Long.valueOf(0);

		if (FileCreationTime.getStartfileCreationTime(name) != null) {
			fileStart = FileCreationTime.startTimestamp(name);
			startDiff = (fileStart.getTime() - startDTTime.getTime());
		}
		if (FileCreationTime.getEndfileCreationTime(name) != null) {
			fileEnd = FileCreationTime.endTimestamp(name);
			endDiff = (fileEnd.getTime() - endDTTime.getTime());
		}
		WtsProcessTab processObj = processDAO.getProcessById(processId);
		Timestamp startProcessTime = null;
		Timestamp endProcessTime = null;

		if (processObj != null) {
			startProcessTime = processObj.getExpectedStartTime();
			endProcessTime = processObj.getExpectedEndTime();
		}

		List<WtsProcessAppMapTab> apps = proAppMapDao.getAllAppMappingsForProcess(processId);

		// start Change case
		if (startDiff > 0) {
			this.updateDifferencePROCESSETA(startDiff, origDiff, etaLst, apps, startProcessTime, endProcessTime,
					fileStart.getTime(), processId, false);
		}
		if (endDiff > 0) {
			this.updateDifferencePROCESSETA(endDiff, origDiff, etaLst, apps, startProcessTime, endProcessTime,
					fileStart.getTime(), processId, true);

		}

		entityManager.flush();

	}

	public void newEtaCalculationForChilds(int parentId, int childId, int processId, boolean mainpageNav)
			throws ParseException {
		System.out.println("start time loop entered");

		WtsAppTab parentApp = appDAO.getAppById(parentId);
		WtsAppTab childApp = appDAO.getAppById(childId);

		String name = childApp.getName();

		List<WtsNewEtaTab> etaLst = new ArrayList<>();
		Timestamp startDTTime = null;
		Timestamp endDTTime = null;
		int buf = 0;
		int currentSeq = 0;
		WtsAppMappingTab proMap = appMapDao.getAppMappingsByParent(parentId, childId, processId);
		if (proMap != null) {
			startDTTime = proMap.getStartTime();
			endDTTime = proMap.getEndTime();
			buf = proMap.getBufferTime();
			currentSeq = proMap.getSequence();
		}

		Long origDiff = Long.valueOf(0);
		origDiff = endDTTime.getTime() - startDTTime.getTime();

		Timestamp fileStart = null;
		Timestamp fileEnd = null;
		Timestamp lastEnd = null;
		Long endDiff = Long.valueOf(0);
		Long startDiff = Long.valueOf(0);

		if (FileCreationTime.getStartfileCreationTime(name) != null) {
			fileStart = FileCreationTime.startTimestamp(name);
			startDiff = (fileStart.getTime() - startDTTime.getTime());
		}
		if (FileCreationTime.getEndfileCreationTime(name) != null) {
			fileEnd = FileCreationTime.endTimestamp(name);
			endDiff = (fileEnd.getTime() - endDTTime.getTime());
		}

		// problem area doubt for parent ETA update-RED BLOCK
		Timestamp startProcessTime = null;
		Timestamp endProcessTime = null;
		if (!mainpageNav) {
			WtsAppMappingTab parentMap = appMapDao.getAppMappingsByParent(parentId, childId, processId);
			if (parentMap != null) {
				startProcessTime = parentMap.getStartTime();
				endProcessTime = parentMap.getEndTime();
			}
		} else {
			WtsProcessAppMapTab parentMap = proAppMapDao.getAllAppMappingsByProcess(processId, parentId);
			if (parentMap != null) {
				startProcessTime = parentMap.getStartTime();
				endProcessTime = parentMap.getEndTime();
			}
		}
		List<WtsAppMappingTab> apps = appMapDao.getAllAppMappingsByParent(parentId, processId);

		// start Change case
		if (startDiff > 0) {
			this.updateDifferenceETA(startDiff, origDiff, etaLst, apps, startProcessTime, endProcessTime,
					fileStart.getTime(), parentId, childId, processId, false);

		}
		if (endDiff > 0) {
			this.updateDifferenceETA(endDiff, origDiff, etaLst, apps, startProcessTime, endProcessTime,
					fileStart.getTime(), parentId, childId, processId, true);

		}
		entityManager.flush();
		// System.out.println("new start time is "+fileStart);
		System.out.println("ETA set for start transations..");
	}

	public void updateDifferenceETA(Long diff, Long origDiff, List<WtsNewEtaTab> etaLst, List<WtsAppMappingTab> apps,
			Timestamp startParentTime, Timestamp endParentTime, Long fileStartTime, int parentId, int childId,
			int processId, boolean endFlow) {

		Iterator<WtsAppMappingTab> apIt = apps.iterator();
		int buf = 0;
		int currentSeq = 0;

		while (apIt.hasNext()) {
			WtsAppMappingTab nextApp = (WtsAppMappingTab) apIt.next();

			int nextAppSeq = 0;
			Timestamp startApp = null;
			Timestamp endDtTime = null;
			if (nextApp != null) {
				startApp = nextApp.getStartTime();
				endDtTime = nextApp.getEndTime();
				buf = nextApp.getBufferTime();
				nextAppSeq = nextApp.getSequence();
			}
			if (nextAppSeq > currentSeq) {

				this.addNewETAForApps(diff, etaLst, nextApp);
			} else if (nextAppSeq == currentSeq) {
				WtsNewEtaTab et = getTdyETATxnByParentChildID(nextApp.getParentId(), nextApp.getChildId(),
						nextApp.getProcessId(), TreatmentDate.getInstance().getTreatmentDate());
				if (et == null) {
					et = new WtsNewEtaTab();
				}
				Timestamp newEnd = null;
				Timestamp newStart = null;
				if (!endFlow) {
					Long newStartL = fileStartTime;
					newStart = new Timestamp(newStartL);
					Long newEndL = origDiff + newStartL;
					newEnd = new Timestamp(newEndL);
					endParentTime = new Timestamp(diff + endParentTime.getTime());
				} else {
					Long newStartL = fileStartTime;
					newStart = new Timestamp(newStartL);
					Long newEndL = diff + endDtTime.getTime();
					newEnd = new Timestamp(newEndL);
					endParentTime = new Timestamp(diff + endParentTime.getTime());
				}
				et.setNewEtaEndTransaction(newEnd);
				et.setNewEtaStartTransaction(newStart);
				et.setParentId(nextApp.getParentId());
				et.setChildId(nextApp.getChildId());
				et.setProcessId(nextApp.getProcessId());
				et.setEventDate(TreatmentDate.getInstance().getTreatmentDate());
				et.setProblemFlag(1);
				etaLst.add(et);

			}

		}
		// update process ETA
		WtsNewEtaTab exist_proc_eta = getTdyETATxnByParentId(parentId, processId,
				TreatmentDate.getInstance().getTreatmentDate());
		if (exist_proc_eta == null) {
			exist_proc_eta = new WtsNewEtaTab();
		}

		exist_proc_eta.setProcessId(processId);
		exist_proc_eta.setParentId(parentId);
		exist_proc_eta.setEventDate(TreatmentDate.getInstance().getTreatmentDate());
		exist_proc_eta.setProblemFlag(0);
		endParentTime = new Timestamp(diff + endParentTime.getTime());
		exist_proc_eta.setNewEtaEndTransaction(endParentTime);
		exist_proc_eta.setNewEtaStartTransaction(startParentTime);
		etaLst.add(exist_proc_eta);

		if (!etaLst.isEmpty()) {
			Iterator<WtsNewEtaTab> etaItr = etaLst.iterator();
			while (etaItr.hasNext()) {
				WtsNewEtaTab wtsNewEtaTab = (WtsNewEtaTab) etaItr.next();
				addNewEta(wtsNewEtaTab);
			}
		}

	}

	public void updateDifferencePROCESSETA(Long diff, Long origDiff, List<WtsNewEtaTab> etaLst,
			List<WtsProcessAppMapTab> apps, Timestamp startProcessTime, Timestamp endProcessTime, Long fileStartTime,
			int processId, boolean endFlow) {

		int buf = 0;
		int currentSeq = 0;

		Iterator<WtsProcessAppMapTab> apIt = apps.iterator();
		while (apIt.hasNext()) {
			WtsProcessAppMapTab nextApp = (WtsProcessAppMapTab) apIt.next();
			// WtsProcessAppMapTab
			// nextApp=proAppMapDao.getAllAppMappingsByProcess(processId,
			// nextApp.getApplicationId());
			int nextAppSeq = 0;
			Timestamp startApp = null;
			Timestamp endDtTime = null;
			if (nextApp != null) {
				startApp = nextApp.getStartTime();
				endDtTime = nextApp.getEndTime();
				buf = nextApp.getBufferTime();
				nextAppSeq = nextApp.getSequence();
			}
			if (nextAppSeq > currentSeq) {

				this.addNewETAForProcessApps(diff, etaLst, nextApp);
			} else if (nextAppSeq == currentSeq) {
				WtsNewEtaTab et = getTdyETATxnByProcessIdAppID(nextApp.getApplicationId(), processId,
						TreatmentDate.getInstance().getTreatmentDate());
				if (et == null) {
					et = new WtsNewEtaTab();
				}
				Timestamp newEnd = null;
				Timestamp newStart = null;
				if (!endFlow) {
					Long newEndL = diff + endDtTime.getTime();
					newEnd = new Timestamp(newEndL);
					Long newStartL = diff + startApp.getTime();
					newStart = new Timestamp(newStartL);
				} else {
					Long newStartL = fileStartTime;
					newStart = new Timestamp(newStartL);
					Long newEndL = diff + endDtTime.getTime();
					newEnd = new Timestamp(newEndL);
					endProcessTime = new Timestamp(diff + endProcessTime.getTime());
				}
				et.setNewEtaEndTransaction(newEnd);
				et.setNewEtaStartTransaction(newStart);
				et.setApplicationId(nextApp.getApplicationId());
				et.setProcessId(processId);
				et.setEventDate(TreatmentDate.getInstance().getTreatmentDate());
				et.setProblemFlag(1);
				etaLst.add(et);
			}

		}
		// update process ETA
		WtsNewEtaTab exist_proc_eta = getTdyETATxnByProcessId(processId,
				TreatmentDate.getInstance().getTreatmentDate());
		if (exist_proc_eta == null) {
			exist_proc_eta = new WtsNewEtaTab();
		}

		exist_proc_eta.setProcessId(processId);
		exist_proc_eta.setEventDate(TreatmentDate.getInstance().getTreatmentDate());
		exist_proc_eta.setProblemFlag(0);
		exist_proc_eta.setNewEtaEndTransaction(endProcessTime);
		exist_proc_eta.setNewEtaStartTransaction(startProcessTime);
		etaLst.add(exist_proc_eta);

		if (!etaLst.isEmpty()) {
			Iterator<WtsNewEtaTab> etaItr = etaLst.iterator();
			while (etaItr.hasNext()) {
				WtsNewEtaTab wtsNewEtaTab = (WtsNewEtaTab) etaItr.next();
				addNewEta(wtsNewEtaTab);
			}
		}

	}

	// Called recursively
	public List<WtsNewEtaTab> addNewETAForApps(Long endDiff, List<WtsNewEtaTab> etaLst, WtsAppMappingTab curApp) {
		WtsNewEtaTab et = getTdyETATxnByParentChildID(curApp.getParentId(), curApp.getChildId(), curApp.getProcessId(),
				TreatmentDate.getInstance().getTreatmentDate());
		if (et == null) {
			et = new WtsNewEtaTab();
		}

		Long newStartL = endDiff + curApp.getStartTime().getTime();
		Timestamp newStart = new Timestamp(newStartL);
		Long newEndL = endDiff + curApp.getEndTime().getTime();
		Timestamp newEnd = new Timestamp(newEndL);
		et.setNewEtaEndTransaction(newEnd);
		et.setNewEtaStartTransaction(newStart);
		et.setParentId(curApp.getParentId());
		et.setChildId(curApp.getChildId());
		et.setProcessId(curApp.getProcessId());
		et.setEventDate(TreatmentDate.getInstance().getTreatmentDate());
		et.setProblemFlag(0);
		etaLst.add(et);

		return etaLst;
	}

	public List<WtsNewEtaTab> addNewETAForProcessApps(Long endDiff, List<WtsNewEtaTab> etaLst,
			WtsProcessAppMapTab curApp) {
		WtsNewEtaTab et = getTdyETATxnByProcessIdAppID(curApp.getApplicationId(), curApp.getProcessId(),
				TreatmentDate.getInstance().getTreatmentDate());
		if (et == null) {
			et = new WtsNewEtaTab();
		}

		Long newStartL = endDiff + curApp.getStartTime().getTime();
		Timestamp newStart = new Timestamp(newStartL);
		Long newEndL = endDiff + curApp.getEndTime().getTime();
		Timestamp newEnd = new Timestamp(newEndL);
		et.setNewEtaEndTransaction(newEnd);
		et.setNewEtaStartTransaction(newStart);
		et.setApplicationId(curApp.getApplicationId());
		et.setProcessId(curApp.getProcessId());
		et.setProcessId(curApp.getProcessId());
		et.setEventDate(TreatmentDate.getInstance().getTreatmentDate());
		et.setProblemFlag(0);
		etaLst.add(et);

		return etaLst;
	}

	public void refreshETA(WtsAppTab app, int processId) throws ParseException {
		System.out.println("refresh ETA entered");
		String name = app.getName();
		int apId = app.getApplicationId();

		List<WtsNewEtaTab> etaLst = new ArrayList<>();
		Timestamp startDTTime = null;
		Timestamp endDTTime = null;
		int buf = 0;
		int currentSeq = 0;
		WtsProcessAppMapTab proMap = proAppMapDao.getAllAppMappingsByProcess(processId, apId);
		if (proMap != null) {
			startDTTime = proMap.getStartTime();
			endDTTime = proMap.getEndTime();
			buf = proMap.getBufferTime();
			currentSeq = proMap.getSequence();
		}

		Long origDiff = Long.valueOf(0);
		origDiff = endDTTime.getTime() - startDTTime.getTime();

		Timestamp fileStart = null;
		Timestamp fileEnd = null;
		Timestamp lastEnd = null;
		Long endDiff = Long.valueOf(0);
		Long startDiff = Long.valueOf(0);

		if (FileCreationTime.getStartfileCreationTime(name) != null) {
			fileStart = FileCreationTime.startTimestamp(name);
			startDiff = (fileStart.getTime() - startDTTime.getTime());
		}
		if (FileCreationTime.getEndfileCreationTime(name) != null) {
			fileEnd = FileCreationTime.endTimestamp(name);
			endDiff = (fileEnd.getTime() - endDTTime.getTime());
		}
		WtsProcessTab processObj = processDAO.getProcessById(processId);
		Timestamp startProcessTime = null;
		Timestamp endProcessTime = null;

		if (processObj != null) {
			startProcessTime = processObj.getExpectedStartTime();
			endProcessTime = processObj.getExpectedEndTime();
		}

		List apps = appDAO.getAllAppsByProcess(processId);

		// start Change case
		if (startDiff != 0) {
			// Timestamp start= new Timestamp(StartDiff);
			System.out.println("in start DIFF");
			Iterator<WtsAppTab> apItr = apps.iterator();
			while (apItr.hasNext()) {
				WtsAppTab nextApp = (WtsAppTab) apItr.next();
				WtsProcessAppMapTab proNxtMap = proAppMapDao.getAllAppMappingsByProcess(processId,
						nextApp.getApplicationId());
				int nextAppSeq = 0;
				Timestamp startappTime = null;
				Timestamp endDtTime = null;
				if (proNxtMap != null) {
					startappTime = proNxtMap.getStartTime();
					endDtTime = proNxtMap.getEndTime();
					buf = proNxtMap.getBufferTime();
					nextAppSeq = proNxtMap.getSequence();
				}
				if (nextAppSeq > currentSeq) {
					WtsNewEtaTab et = getTdyETATxnByProcessIdAppID(nextApp.getApplicationId(), processId,
							TreatmentDate.getInstance().getTreatmentDate());
					if (et == null) {
						et = new WtsNewEtaTab();
					}

					Long newEndL = startDiff + endDtTime.getTime();
					Timestamp newEnd = new Timestamp(newEndL);
					Long newStartL = startDiff + startappTime.getTime();
					Timestamp newStart = new Timestamp(newStartL);
					if(newStart.after(endDtTime)){
                        et.setNewEtaEndTransaction(newEnd);  
                        et.setNewEtaStartTransaction(newStart);
                        }
                        else {
                               et.setNewEtaEndTransaction(endDtTime);
                               et.setNewEtaStartTransaction(startappTime);
                        }
					// et.setApplicationId(nextApp.getApplicationId());
					// et.setProcessId(processId);
					// et.setEventDate(TreatmentDate.getInstance().getTreatmentDate());
					// et.setProblemFlag(0);
					etaLst.add(et);
					// emailService.SendMailAlertNewEta(nextApp.getEmailId());

				} else if (nextAppSeq == currentSeq) {
					WtsNewEtaTab et = getTdyETATxnByProcessIdAppID(nextApp.getApplicationId(), processId,
							TreatmentDate.getInstance().getTreatmentDate());
					if (et == null) {
						et = new WtsNewEtaTab();
					}
					Long newStartL = fileStart.getTime();
					Timestamp newStart = new Timestamp(newStartL);
					Long newEndL = origDiff + newStartL;
					Timestamp newEnd = new Timestamp(newEndL);
					endProcessTime = new Timestamp(startDiff + endProcessTime.getTime());
					if(newStart.after(endDtTime)){
                        et.setNewEtaEndTransaction(newEnd);  
                        et.setNewEtaStartTransaction(newStart);
                        }
                        else {
                               et.setNewEtaEndTransaction(endDtTime);
                               et.setNewEtaStartTransaction(startappTime);
                        }

					// et.setApplicationId(nextApp.getApplicationId());
					// et.setProcessId(processId);
					// et.setEventDate(TreatmentDate.getInstance().getTreatmentDate());
					// et.setProblemFlag(1);
					etaLst.add(et);

				}

			}
			// update process ETA
			WtsNewEtaTab exist_proc_eta = getTdyETATxnByProcessId(processId,
					TreatmentDate.getInstance().getTreatmentDate());
			if (exist_proc_eta == null) {
				exist_proc_eta = new WtsNewEtaTab();
			}

			exist_proc_eta.setProcessId(processId);
			exist_proc_eta.setEventDate(TreatmentDate.getInstance().getTreatmentDate());
			exist_proc_eta.setProblemFlag(0);
			exist_proc_eta.setNewEtaEndTransaction(endProcessTime);
			exist_proc_eta.setNewEtaStartTransaction(startProcessTime);
			etaLst.add(exist_proc_eta);

			if (!etaLst.isEmpty()) {
				Iterator<WtsNewEtaTab> etaItr = etaLst.iterator();
				while (etaItr.hasNext()) {
					WtsNewEtaTab wtsNewEtaTab = (WtsNewEtaTab) etaItr.next();
					addNewEta(wtsNewEtaTab);
				}
			}

		}
		if (endDiff != 0) {
			System.out.println("in end DIFF");
			Iterator<WtsAppTab> apIt = apps.iterator();
			while (apIt.hasNext()) {
				WtsAppTab nextApp = (WtsAppTab) apIt.next();
				WtsProcessAppMapTab proNxtMap = proAppMapDao.getAllAppMappingsByProcess(processId,
						nextApp.getApplicationId());
				int nextAppSeq = 0;
				Timestamp startApp = null;
				Timestamp endDtTime = null;
				if (proNxtMap != null) {
					startApp = proNxtMap.getStartTime();
					endDtTime = proNxtMap.getEndTime();
					buf = proNxtMap.getBufferTime();
					nextAppSeq = proNxtMap.getSequence();
				}
				if (nextAppSeq > currentSeq) {
					WtsNewEtaTab et = getTdyETATxnByProcessIdAppID(nextApp.getApplicationId(), processId,
							TreatmentDate.getInstance().getTreatmentDate());
					if (et == null) {
						et = new WtsNewEtaTab();
					}

					Long newStartL = endDiff + startApp.getTime();
					Timestamp newStart = new Timestamp(newStartL);
					Long newEndL = endDiff + endDtTime.getTime();
					Timestamp newEnd = new Timestamp(newEndL);
					if(newEnd.after(endDtTime)){
                        et.setNewEtaEndTransaction(newEnd);  
                        et.setNewEtaStartTransaction(newStart);
                        }
                        else {
                               et.setNewEtaEndTransaction(endDtTime);
                               et.setNewEtaStartTransaction(startApp);
                        }

					// et.setApplicationId(nextApp.getApplicationId());
					// et.setProcessId(processId);
					// et.setEventDate(TreatmentDate.getInstance().getTreatmentDate());
					// et.setProblemFlag(0);
					etaLst.add(et);

				} else if (nextAppSeq == currentSeq) {
					WtsNewEtaTab et = getTdyETATxnByProcessIdAppID(nextApp.getApplicationId(), processId,
							TreatmentDate.getInstance().getTreatmentDate());
					if (et == null) {
						et = new WtsNewEtaTab();
					}

					Long newStartL = fileStart.getTime();
					Timestamp newStart = new Timestamp(newStartL);
					Long newEndL = endDiff + endDtTime.getTime();
					Timestamp newEnd = new Timestamp(newEndL);
					endProcessTime = new Timestamp(endDiff + endProcessTime.getTime());
					if(newEnd.after(endDtTime)){
                        et.setNewEtaEndTransaction(newEnd);  
                        et.setNewEtaStartTransaction(newStart);
                        }
                        else {
                               et.setNewEtaEndTransaction(endDtTime);
                               et.setNewEtaStartTransaction(startApp);
                        }

					// et.setApplicationId(nextApp.getApplicationId());
					// et.setProcessId(processId);
					// et.setEventDate(TreatmentDate.getInstance().getTreatmentDate());
					// et.setProblemFlag(1);
					etaLst.add(et);
				}

			}
			// update process ETA
			WtsNewEtaTab exist_proc_eta = getTdyETATxnByProcessId(processId,
					TreatmentDate.getInstance().getTreatmentDate());
			if (exist_proc_eta == null) {
				exist_proc_eta = new WtsNewEtaTab();
			}

			exist_proc_eta.setProcessId(processId);
			exist_proc_eta.setEventDate(TreatmentDate.getInstance().getTreatmentDate());
			exist_proc_eta.setProblemFlag(0);
			exist_proc_eta.setNewEtaEndTransaction(endProcessTime);
			exist_proc_eta.setNewEtaStartTransaction(startProcessTime);
			etaLst.add(exist_proc_eta);

			if (!etaLst.isEmpty()) {
				Iterator<WtsNewEtaTab> etaItr = etaLst.iterator();
				while (etaItr.hasNext()) {
					WtsNewEtaTab wtsNewEtaTab = (WtsNewEtaTab) etaItr.next();
					addNewEta(wtsNewEtaTab);
				}
			}

		}

		entityManager.flush();
		// System.out.println("new start time is "+fileStart);
		System.out.println("refreshed ETA..");
	}

	public void refreshChildETA(int parentId, int childId, int processId, boolean mainpageNav) throws ParseException {
		System.out.println("refreshChildETA entered");
		WtsAppTab parentApp = appDAO.getAppById(parentId);
		WtsAppTab childApp = appDAO.getAppById(childId);

		String name = childApp.getName();

		List<WtsNewEtaTab> etaLst = new ArrayList<>();
		Timestamp startDTTime = null;
		Timestamp endDTTime = null;
		int buf = 0;
		int currentSeq = 0;
		WtsAppMappingTab proMap = appMapDao.getAppMappingsByParent(parentId, childId, processId);
		if (proMap != null) {
			startDTTime = proMap.getStartTime();
			endDTTime = proMap.getEndTime();
			buf = proMap.getBufferTime();
			currentSeq = proMap.getSequence();
		}

		Long origDiff = Long.valueOf(0);
		origDiff = endDTTime.getTime() - startDTTime.getTime();

		Timestamp fileStart = null;
		Timestamp fileEnd = null;
		Timestamp lastEnd = null;
		Long endDiff = Long.valueOf(0);
		Long startDiff = Long.valueOf(0);

		if (FileCreationTime.getStartfileCreationTime(name) != null) {
			fileStart = FileCreationTime.startTimestamp(name);
			startDiff = (fileStart.getTime() - startDTTime.getTime());
		}
		if (FileCreationTime.getEndfileCreationTime(name) != null) {
			fileEnd = FileCreationTime.endTimestamp(name);
			endDiff = (fileEnd.getTime() - endDTTime.getTime());
		}
		WtsProcessTab processObj = processDAO.getProcessById(processId);
		Timestamp startProcessTime = null;
		Timestamp endProcessTime = null;

		if (!mainpageNav) {
			WtsAppMappingTab parentMap = appMapDao.getAppMappingsByParent(parentId, childId, processId);
			if (parentMap != null) {
				startProcessTime = parentMap.getStartTime();
				endProcessTime = parentMap.getEndTime();
			}
		} else {
			WtsProcessAppMapTab parentMap = proAppMapDao.getAllAppMappingsByProcess(processId, parentId);
			if (parentMap != null) {
				startProcessTime = parentMap.getStartTime();
				endProcessTime = parentMap.getEndTime();
			}
		}
		List<WtsAppMappingTab> apps = appMapDao.getAllAppMappingsByParent(parentId, processId);

		// start Change case
		if (startDiff != 0) {
			// Timestamp start= new Timestamp(StartDiff);
			System.out.println("in start DIFF");
			Iterator<WtsAppMappingTab> apItr = apps.iterator();
			while (apItr.hasNext()) {
				WtsAppMappingTab nextApp = (WtsAppMappingTab) apItr.next();

				int nextAppSeq = 0;
				Timestamp startappTime = null;
				Timestamp endDtTime = null;
				if (nextApp != null) {
					startappTime = nextApp.getStartTime();
					endDtTime = nextApp.getEndTime();
					buf = nextApp.getBufferTime();
					nextAppSeq = nextApp.getSequence();
				}
				if (nextAppSeq > currentSeq) {
					WtsNewEtaTab et = getTdyETATxnByParentChildID(nextApp.getParentId(), nextApp.getChildId(),
							nextApp.getProcessId(), TreatmentDate.getInstance().getTreatmentDate());
					if (et == null) {
						et = new WtsNewEtaTab();
					}

					Long newEndL = startDiff + endDtTime.getTime();
					Timestamp newEnd = new Timestamp(newEndL);
					Long newStartL = startDiff + startappTime.getTime();
					Timestamp newStart = new Timestamp(newStartL);
					et.setNewEtaEndTransaction(newEnd);
					et.setNewEtaStartTransaction(newStart);
					etaLst.add(et);

				} else if (nextAppSeq == currentSeq) {
					WtsNewEtaTab et = getTdyETATxnByParentChildID(nextApp.getParentId(), nextApp.getChildId(),
							nextApp.getProcessId(), TreatmentDate.getInstance().getTreatmentDate());
					if (et == null) {
						et = new WtsNewEtaTab();
					}
					Long newStartL = fileStart.getTime();
					Timestamp newStart = new Timestamp(newStartL);
					Long newEndL = origDiff + newStartL;
					Timestamp newEnd = new Timestamp(newEndL);
					endProcessTime = new Timestamp(startDiff + endProcessTime.getTime());
					et.setNewEtaEndTransaction(newEnd);
					et.setNewEtaStartTransaction(newStart);
					etaLst.add(et);

				}

			}
			// update process ETA
			WtsNewEtaTab exist_proc_eta = getTdyETATxnByParentId(parentId, processId,
					TreatmentDate.getInstance().getTreatmentDate());
			if (exist_proc_eta == null) {
				exist_proc_eta = new WtsNewEtaTab();
			}

			exist_proc_eta.setProcessId(processId);
			exist_proc_eta.setEventDate(TreatmentDate.getInstance().getTreatmentDate());
			exist_proc_eta.setProblemFlag(0);
			exist_proc_eta.setNewEtaEndTransaction(endProcessTime);
			exist_proc_eta.setNewEtaStartTransaction(startProcessTime);
			etaLst.add(exist_proc_eta);

			if (!etaLst.isEmpty()) {
				Iterator<WtsNewEtaTab> etaItr = etaLst.iterator();
				while (etaItr.hasNext()) {
					WtsNewEtaTab wtsNewEtaTab = (WtsNewEtaTab) etaItr.next();
					addNewEta(wtsNewEtaTab);
				}
			}

		}
		if (endDiff != 0) {
			System.out.println("in end DIFF");
			Iterator<WtsAppMappingTab> apIt = apps.iterator();
			while (apIt.hasNext()) {
				WtsAppMappingTab nextApp = (WtsAppMappingTab) apIt.next();

				int nextAppSeq = 0;
				Timestamp startApp = null;
				Timestamp endDtTime = null;
				if (nextApp != null) {
					startApp = nextApp.getStartTime();
					endDtTime = nextApp.getEndTime();
					buf = nextApp.getBufferTime();
					nextAppSeq = nextApp.getSequence();
				}
				if (nextAppSeq > currentSeq) {
					WtsNewEtaTab et = getTdyETATxnByParentChildID(nextApp.getParentId(), nextApp.getChildId(),
							nextApp.getProcessId(), TreatmentDate.getInstance().getTreatmentDate());
					if (et == null) {
						et = new WtsNewEtaTab();
					}

					Long newStartL = endDiff + startApp.getTime();
					Timestamp newStart = new Timestamp(newStartL);
					Long newEndL = endDiff + endDtTime.getTime();
					Timestamp newEnd = new Timestamp(newEndL);
					et.setNewEtaEndTransaction(newEnd);
					et.setNewEtaStartTransaction(newStart);
					etaLst.add(et);

				} else if (nextAppSeq == currentSeq) {
					WtsNewEtaTab et = getTdyETATxnByParentChildID(nextApp.getParentId(), nextApp.getChildId(),
							nextApp.getProcessId(), TreatmentDate.getInstance().getTreatmentDate());
					if (et == null) {
						et = new WtsNewEtaTab();
					}

					Long newStartL = fileStart.getTime();
					Timestamp newStart = new Timestamp(newStartL);
					Long newEndL = endDiff + endDtTime.getTime();
					Timestamp newEnd = new Timestamp(newEndL);
					endProcessTime = new Timestamp(endDiff + endProcessTime.getTime());
					et.setNewEtaEndTransaction(newEnd);
					et.setNewEtaStartTransaction(newStart);
					etaLst.add(et);
				}

			}
			// update process ETA
			WtsNewEtaTab exist_proc_eta = getTdyETATxnByParentId(parentId, processId,
					TreatmentDate.getInstance().getTreatmentDate());
			if (exist_proc_eta == null) {
				exist_proc_eta = new WtsNewEtaTab();
			}

			exist_proc_eta.setProcessId(processId);
			exist_proc_eta.setEventDate(TreatmentDate.getInstance().getTreatmentDate());
			exist_proc_eta.setProblemFlag(0);
			exist_proc_eta.setNewEtaEndTransaction(endProcessTime);
			exist_proc_eta.setNewEtaStartTransaction(startProcessTime);
			etaLst.add(exist_proc_eta);

			if (!etaLst.isEmpty()) {
				Iterator<WtsNewEtaTab> etaItr = etaLst.iterator();
				while (etaItr.hasNext()) {
					WtsNewEtaTab wtsNewEtaTab = (WtsNewEtaTab) etaItr.next();
					addNewEta(wtsNewEtaTab);
				}
			}

		}

		entityManager.flush();
		// System.out.println("new start time is "+fileStart);
		System.out.println("refreshed ETA..");
	}

	private WtsNewEtaTab getTdyETATxnByProcessId(int processId, String treatmentDate) {
		String hql = "from WtsNewEtaTab WHERE processId=?  AND eventDate= ? and applicationId=0";
		Query qry = entityManager.createQuery(hql);
		qry.setParameter(1, processId);
		qry.setParameter(2, treatmentDate);
		if (qry.getResultList() != null && !qry.getResultList().isEmpty())
			return (WtsNewEtaTab) qry.getResultList().get(0);
		else
			return null;

	}

	private WtsNewEtaTab getTdyETATxnByParentId(int parentId, int processId, String treatmentDate) {
		String hql = "from WtsNewEtaTab WHERE processId=?  AND eventDate= ?  AND parentId=? and applicationId=0 and childId=0";
		Query qry = entityManager.createQuery(hql);
		qry.setParameter(1, processId);
		qry.setParameter(2, treatmentDate);
		qry.setParameter(3, parentId);
		if (qry.getResultList() != null && !qry.getResultList().isEmpty())
			return (WtsNewEtaTab) qry.getResultList().get(0);
		else
			return null;

	}

	public void updateGreenDay(int processId, String treatmentDate) {
		String hql = "UPDATE WtsNewEtaTab SET problemFlag= 0 WHERE processId=?  AND eventDate= ?";
		Query qry = entityManager.createQuery(hql);
		qry.setParameter(1, processId);
		qry.setParameter(2, treatmentDate);
		qry.executeUpdate();
	}

	public void updateGreenDayForChilds(int processId, int parentId, String treatmentDate) {
		String hql = "UPDATE WtsNewEtaTab SET problemFlag= 0 WHERE processId=?  AND eventDate= ? AND parentId=?";
		Query qry = entityManager.createQuery(hql);
		qry.setParameter(1, processId);
		qry.setParameter(2, treatmentDate);
		qry.setParameter(3, parentId);
		qry.executeUpdate();
	}

	public WtsNewEtaTab getTdyETATxnByProcessIdAppID(int appId, int processid, String treatDt) {

		String hql = "from WtsNewEtaTab WHERE processId=? AND applicationId=? AND eventDate= ?";
		Query qry = entityManager.createQuery(hql);
		qry.setParameter(1, processid);
		qry.setParameter(2, appId);
		qry.setParameter(3, treatDt);
		if (qry.getResultList() != null && !qry.getResultList().isEmpty())
			return (WtsNewEtaTab) qry.getResultList().get(0);
		else
			return null;

	}

	public WtsNewEtaTab getTdyETATxnByParentChildID(int parentId, int childId, int processid, String treatDt) {

		String hql = "from WtsNewEtaTab WHERE processId=? AND parentId=? AND childId=? AND eventDate= ?";
		Query qry = entityManager.createQuery(hql);
		qry.setParameter(1, processid);
		qry.setParameter(2, parentId);
		qry.setParameter(3, childId);
		qry.setParameter(4, treatDt);
		if (qry.getResultList() != null && !qry.getResultList().isEmpty())
			return (WtsNewEtaTab) qry.getResultList().get(0);
		else
			return null;

	}

	public List<WtsNewEtaTab> getEtaByAppIdProcessId(int processId, int appId) {
		try {
			String hql = "From WtsNewEtaTab where applicationId=? and processId=? and eventDate=?";
			List<WtsNewEtaTab> et = (List<WtsNewEtaTab>) entityManager.createQuery(hql).setParameter(1, appId)
					.setParameter(2, processId).setParameter(3, TreatmentDate.getInstance().getTreatmentDate())
					.getSingleResult();
			return et;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public WtsNewEtaTab EtaProcessTime(int processId) {
		WtsNewEtaTab eta = new WtsNewEtaTab();
		WtsNewEtaTab etS = new WtsNewEtaTab();
		WtsNewEtaTab etE = new WtsNewEtaTab();
		int fApp = appDAO.getFirstAppId(processId);
		int lApp = appDAO.getLastAppId(processId);
		etS = (WtsNewEtaTab) getEtaByAppIdProcessId(processId, fApp);
		etE = (WtsNewEtaTab) getEtaByAppIdProcessId(processId, lApp);
		if (etS != null) {
			eta.setNewEtaStartTransaction(etS.getNewEtaStartTransaction());
		} else {
			eta.setNewEtaEndTransaction(proAppMapDao.getAppMappingStartTime(processId, fApp));
		}

		if (etE != null) {
			eta.setNewEtaEndTransaction(etS.getNewEtaEndTransaction());
		} else {
			eta.setNewEtaEndTransaction(proAppMapDao.getAppMappingEndTime(processId, lApp));
		}

		eta.setProcessId(processId);
		eta.setApplicationId(0);
		eta.setEventDate(TreatmentDate.getInstance().getTreatmentDate());
		eta.setProblemFlag(1);
		return eta;
	}

	public List<WtsNewEtaTab> getAllEta() {
		String hql = "FROM WtsNewEtaTab as et where et.eventDate=?  AND et.parentId IS NULL AND et.childId IS NULL ORDER BY et.applicationId";
		return (List<WtsNewEtaTab>) entityManager.createQuery(hql)
				.setParameter(1, TreatmentDate.getInstance().getTreatmentDate()).getResultList();
	}

	public List<WtsNewEtaTab> getAllChildEta(int parentId, int processId) {
		String hql = "FROM WtsNewEtaTab as et where et.eventDate=? AND et.processId=? AND et.parentId=? ORDER BY et.childId";
		return (List<WtsNewEtaTab>) entityManager.createQuery(hql)
				.setParameter(1, TreatmentDate.getInstance().getTreatmentDate()).setParameter(2, processId)
				.setParameter(3, parentId).getResultList();
	}

	public List<WtsNewEtaTab> getAllCurrentEta() {
		String hql = "FROM WtsNewEtaTab where eventDate=?";
		return (List<WtsNewEtaTab>) entityManager.createQuery(hql)
				.setParameter(1, TreatmentDate.getInstance().getTreatmentDate()).getResultList();
	}

}
