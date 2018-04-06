package com.WTS.Dashboards.dao;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.WTS.Dashboards.Controller.WtsTransTabController;
import com.WTS.Dashboards.Entity.WtsAppTab;
import com.WTS.Dashboards.Entity.WtsNewEtaTab;
import com.WTS.Dashboards.Entity.WtsProcessAppMapTab;
import com.WTS.Dashboards.Entity.WtsProcessTab;
import com.WTS.Dashboards.Entity.WtsTransTab;
import com.WTS.Dashboards.Service.EmailService;
//import com.WTS.Dashboards.Service.EmailService;
import com.WTS.Dashboards.Utility.DateUtility;
import com.WTS.Dashboards.Utility.FileCreationTime;
import com.WTS.Dashboards.Utility.TreatmentDate;

@Transactional
@Repository
public class WtsTransTabDao implements IWtsDaoInterface {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private WtsProcessTabDao processDAO;

	@Autowired
	private WtsAppTabDao appDAO;

	@Autowired
	private WtsNewEtaTabDao etDAO;
	@Autowired
	private EmailService emailService;
	@Autowired
	private WtsProcessAppMapTabDao proAppMapDao;

	public WtsTransTab getTransactionById(int transactionId) {
		return entityManager.find(WtsTransTab.class, transactionId);
	}

	// public WtsTransTab getTransactionByProcessId(int processId) {
	// System.out.println("getTransactionByProcessId(int processId)");
	// return entityManager.find(WtsTransTab.class,processId);
	// }
	//
	@SuppressWarnings("unchecked")

	public List<WtsTransTab> getAlltransaction() {
		String hql = "FROM WtsTransTab as tran ORDER BY tran.transactionId";
		return (List<WtsTransTab>) entityManager.createQuery(hql).getResultList();
	}

	public void addTransaction(WtsTransTab transaction, String name) {
		try {
			transaction.setEventDate(TreatmentDate.getInstance().getTreatmentDate());

			if (FileCreationTime.getStartfileCreationTime(name) != null)
				transaction.setStartTransaction(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.parse(FileCreationTime.getStartfileCreationTime(name)));
			if (FileCreationTime.getEndfileCreationTime(name) != null)
				transaction.setEndTransaction(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.parse(FileCreationTime.getEndfileCreationTime(name)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// transaction.setStatusId(FileCreationTime.checkFileStatus());

		entityManager.persist(transaction);
		entityManager.flush();

	}

	/*
	 * public void addTransaction(WtsTransTab transaction) { try {
	 * transaction.setEventDate(TreatmentDate.getInstance().getTreatmentDate());
	 * if(FileCreationTime.getStartfileCreationTime(name)!=null)
	 * transaction.setStartTransaction(new
	 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(FileCreationTime.
	 * getStartfileCreationTime(name))); } catch (ParseException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); }
	 * transaction.setStatusId(FileCreationTime.checkFileStatus());
	 * 
	 * entityManager.persist(transaction); entityManager.flush();
	 * 
	 * }
	 */

	public void updateTransaction(WtsTransTab transaction) {
		WtsTransTab tran = getTransactionById(transaction.getTransactionId());
		System.out.println("Check---------> by transactionID");
		tran.setEventDate(transaction.getEventDate());
		tran.setStatusId(transaction.getStatusId());
		entityManager.flush();
	}

	public void deleteTransaction(int transactionId) {
		entityManager.remove(getTransactionById(transactionId));
	}

	/*
	 * public boolean transactionExists(int transactionId) {
	 * System.out.println("transaction Exists ");
	 * 
	 * String hql = "FROM WtsTransTab as trans WHERE trans.transaction_id= ? "; int
	 * count = entityManager.createQuery(hql).setParameter(1,
	 * transactionId).getResultList().size(); entityManager.flush(); return count >
	 * 0 ? true : false; }
	 */
	public WtsTransTab getTdyTxnByProcessId(int processId, String trtDt) {
		String hql = "from WtsTransTab WHERE processId=? and eventDate= ? AND application_id IS null and app_mapping_id IS null";

		Query qry = entityManager.createQuery(hql);
		qry.setParameter(1, processId);
		qry.setParameter(2, trtDt);
		if (qry.getResultList() != null && !qry.getResultList().isEmpty())
			return (WtsTransTab) qry.getResultList().get(0);
		else
			return null;

	}

	public WtsTransTab getTransactionByAppIdProId(int appId, int processid, String treatDt) {
		String hql = "from WtsTransTab WHERE processId=? AND applicationId=? AND eventDate= ?";
		Query qry = entityManager.createQuery(hql);
		qry.setParameter(1, processid);
		qry.setParameter(2, appId);
		qry.setParameter(3, treatDt);
		if (qry.getResultList() != null && !qry.getResultList().isEmpty())
			return (WtsTransTab) qry.getResultList().get(0);
		else
			return null;

	}

	public WtsTransTab getTransactionByAppIdProIdBatId(int appId, int processid, int batchId, Date treatDt) {
		String hql = "from WtsTransTab WHERE processId=? AND applicationId=? AND batchId=? AND eventDate= ?";
		Query qry = entityManager.createQuery(hql);
		qry.setParameter(1, processid);
		qry.setParameter(2, appId);
		qry.setParameter(3, batchId);
		qry.setParameter(4, treatDt);
		if (qry.getResultList() != null && !qry.getResultList().isEmpty())
			return (WtsTransTab) qry.getResultList().get(0);
		else
			return null;

	}

	public WtsTransTab getTdyTxnByProcessIdAppId(int processId, String trtDt, int appId) {
		String hql = "from WtsTransTab WHERE processId=? and eventDate= ? AND application_id= ? and batch_id IS null";

		Query qry = entityManager.createQuery(hql);
		qry.setParameter(1, processId);
		qry.setParameter(2, trtDt);
		qry.setParameter(3, appId);
		if (qry.getResultList() != null && !qry.getResultList().isEmpty())
			return (WtsTransTab) qry.getResultList().get(0);
		else
			return null;

	}

	public boolean transactionExistsbyProcessId(int process) {
		System.out.println("boolean transactionExistsbyProcessId(int Id)");
		String hql = "From WtsTranTab as trans WHERE trans.processId=? ";
		int count = entityManager.createQuery(hql).setParameter(1, process).getResultList().size();
		entityManager.flush();
		return count > 0 ? true : false;
	}

	public List<WtsTransTab> gettranId(int processId) {
		System.out.println("processId" + processId);
		String hql = "FROM WtsTransTab as trans WHERE trans.processId=:proc";

		List<WtsTransTab> ls = entityManager.createQuery(hql).setParameter("proc", processId).getResultList();

		return ls;
	}

	public void addProcessTransaction(WtsTransTab transaction) {

		transaction.setEventDate(TreatmentDate.getInstance().getTreatmentDate());

		transaction.setStatusId(0);

		entityManager.persist(transaction);
		entityManager.flush();

	}

	public void updateTransactionModifiedDetail(WtsTransTab trans) throws Exception {
		WtsTransTab transa = (WtsTransTab) trans;

		Timestamp startDTTime = null;
		Timestamp endDtTime = null;
		Timestamp startModDTTime = null;
		Timestamp endModDtTime = null;
		int bufferTime = 0;
		int curSeq =0;
		// APPLICATION
		if (transa.getApplicationId() > 0) {
			WtsAppTab appln = appDAO.getAppById(transa.getApplicationId());
			WtsProcessAppMapTab proMap=proAppMapDao.getAllAppMappingsByProcess(transa.getProcessId(), transa.getApplicationId());
			if(proMap!=null) {
				 startDTTime=proMap.getStartTime();
				 endDtTime=proMap.getEndTime();
				 bufferTime=proMap.getBufferTime();
				 curSeq=proMap.getSequence();
			}
			
			/*
			 * Lokesh start
			 */
			startModDTTime = DateUtility.addBufferTime(startDTTime, bufferTime);
			System.out.println("Endtimefrom database" + endDtTime);

			endModDtTime = DateUtility.addBufferTime(endDtTime, bufferTime);
			WtsNewEtaTab et = etDAO.getTdyETATxnByProcessIdAppID(appln.getApplicationId(), transa.getProcessId(),
					TreatmentDate.getInstance().getTreatmentDate());
			if (et != null) {
				startDTTime = et.getNewEtaStartTransaction();
				endDtTime = et.getNewEtaEndTransaction();

				startModDTTime = DateUtility.addBufferTime(startDTTime, bufferTime);

				endModDtTime = DateUtility.addBufferTime(endDtTime, bufferTime);
			}
			String name=appln.getName();
			int status = getFileStatus(startModDTTime, endModDtTime, name);
			WtsTransTab existtrans = getTdyTxnByProcessIdAppId(transa.getProcessId(),
					TreatmentDate.getInstance().getTreatmentDate(), appln.getApplicationId());
			if (existtrans != null && existtrans.getStatusId() == WtsTransTabController.STATUS_FAILURE
					&& (status == WtsTransTabController.STATUS_IN_PROGRESS
							|| status == WtsTransTabController.STATUS_DELAYED
							|| status == WtsTransTabController.STATUS_FAILURE)) {

				// UPDATE ETA HERE FOR ALL NEXT APPS AND PROCESS
				this.updateNewETA(transa.getProcessId(), existtrans.getApplicationId(), true);

			} else if (existtrans != null && existtrans.getStatusId() == WtsTransTabController.STATUS_FAILURE
					&& (status == WtsTransTabController.STATUS_SUCCESS)) {
				if (FileCreationTime.getStartfileCreationTime(name) != null
						&& (FileCreationTime.getEndfileCreationTime(name) != null)
						&& (FileCreationTime.endTimestamp(name).after(endModDtTime)))
					// UPDATE ETA HERE FOR ALL NEXT APPS AND PROCESS
					this.updateNewETA(transa.getProcessId(), existtrans.getApplicationId(), true);
			}

			System.out.println("getFileStatus function checked and status set");

			transa.setStatusId(status);
			if (FileCreationTime.getStartfileCreationTime(name) != null)
				transa.setStartTransaction(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.parse(FileCreationTime.getStartfileCreationTime(name)));
			if (status == WtsTransTabController.STATUS_SUCCESS) {
				if (FileCreationTime.getEndfileCreationTime(name) != null)
					transa.setEndTransaction(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.parse(FileCreationTime.getEndfileCreationTime(name)));
				System.out.println("start file set time" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.parse(FileCreationTime.getEndfileCreationTime(name)));
			}
			if (status == WtsTransTabController.STATUS_FAILURE) {

				List<WtsAppTab> apps = appDAO.getAllAppsByProcess(transa.getProcessId());
				if (apps != null) {
					Iterator<WtsAppTab> appssItr = apps.iterator();
					while (appssItr.hasNext()) {
						WtsAppTab app = (WtsAppTab) appssItr.next();

						if (trans.getSendemailflag() == 0) {
							emailService.sendMailRedAlert(proAppMapDao.getAppMappingEmailId(transa.getProcessId(), app.getApplicationId()));
							emailService.sendREDalertSMS(proAppMapDao.getAppMappingSupportContact(transa.getProcessId(), app.getApplicationId()));
							trans.setSendemailflag(1);
						}

					}
				}
			}

			// PROCESS
		} else if (transa.getApplicationId() == 0) {
			Date startTxnTime = null;
			Date EndTxnTime = null;
			int startAppID = 0;
			int endAppID = 0;
			int status = 0;
			List<WtsAppTab> apps = appDAO.getAllAppsByProcess(transa.getProcessId());
			
			WtsProcessTab processObj=processDAO.getProcessById(transa.getProcessId());
			Timestamp processExpEndTime=processObj.getExpectedEndTime();
			if (apps != null) {
				Iterator<WtsAppTab> appssItr = apps.iterator();
				int seq = 0;
				int mSeq = appDAO.getLastSeq(processObj.getProcessId());
				while (appssItr.hasNext()) {
					WtsAppTab wtsAppTab = (WtsAppTab) appssItr.next();
					int appseq=proAppMapDao.getAppMappingSequence(transa.getProcessId(), wtsAppTab.getApplicationId());
					if (appseq == 1) {
						startAppID = wtsAppTab.getApplicationId();
						status = WtsTransTabController.STATUS_IN_PROGRESS;
						WtsTransTab appTxn = this.getTransactionByAppIdProId(startAppID, processObj.getProcessId(),
								TreatmentDate.getInstance().getTreatmentDate());
						if (appTxn != null)
							transa.setStartTransaction(appTxn.getStartTransaction());
					}
					if (appseq == mSeq) {
						seq = appseq;
						endAppID = wtsAppTab.getApplicationId();
						String name = wtsAppTab.getName();
						
						WtsProcessAppMapTab proMap=proAppMapDao.getAllAppMappingsByProcess(transa.getProcessId(), endAppID);
						if(proMap!=null) {
							 startDTTime=proMap.getStartTime();
							 endDtTime=proMap.getEndTime();
							 bufferTime=proMap.getBufferTime();
							 
						}
						
						startModDTTime = DateUtility.addBufferTime(startDTTime, bufferTime);

						endModDtTime = DateUtility.addBufferTime(endDtTime, bufferTime);

						WtsNewEtaTab et = etDAO.getTdyETATxnByProcessIdAppID(wtsAppTab.getApplicationId(),
								transa.getProcessId(), TreatmentDate.getInstance().getTreatmentDate());
						if (et != null) {
							startDTTime = et.getNewEtaStartTransaction();
							endDtTime = et.getNewEtaEndTransaction();
							startModDTTime = DateUtility.addBufferTime(startDTTime, bufferTime);

							endModDtTime = DateUtility.addBufferTime(endDtTime, bufferTime);
						}
						status = getFileStatus(startModDTTime, endModDtTime, name);
						WtsTransTab appTxn = this.getTransactionByAppIdProId(endAppID, transa.getProcessId(),
								TreatmentDate.getInstance().getTreatmentDate());
						if (appTxn != null) {
							transa.setEndTransaction(appTxn.getEndTransaction());
						}
						
						if(DateUtility.isAfterOrSame(processExpEndTime, appTxn.getEndTransaction())) {
							etDAO.updateGreenDay(transa.getProcessId(), TreatmentDate.getInstance().getTreatmentDate());
						}

					}
					

				}
			}

			transa.setStatusId(status);

		}

		entityManager.flush();

		

	}

	private void updateNewETA(int processId, int applicationId, boolean isProblem) throws ParseException {
		System.out.println("update new ETA entered");
		// MOVE THIS to the ETA SERVICE

		WtsAppTab app = appDAO.getAppById(applicationId);
		etDAO.newEtaCalculation(app, processId);
	}

	private int getFileStatus(Timestamp startDTTime, Timestamp endDtTime, String name) {
		int finalstatus = 0;
		try {
			System.out.println("getFileStatus Loop entered");

			boolean startFile = FileCreationTime.stratFileExist(name);
			boolean endFile = FileCreationTime.endFileExist(name);
			boolean failFile = FileCreationTime.failFileExist(name);

			Timestamp current = currentTimestamp();
			if ((!startFile) && (!endFile) && (!failFile) && current.after(startDTTime)) {
				finalstatus = 2;
				System.out.println("Delayed RED.");
			} else if ((!startFile) && (!endFile) && (!failFile)) {
				finalstatus = 0;
				System.out.println("not started");
			}

			else if ((!startFile) && (!endFile) && current.after(startDTTime)) {

				finalstatus = 2;
			}

			else if ((startFile) && (!endFile) && (failFile)) {

				finalstatus = 2;
			}

			else if ((startFile) && (!endFile) && (current.after(endDtTime))) {

				// finalstatus=3;
				finalstatus = 2;
				System.out.println("application completion delayed");
			} else if ((startFile) && (!endFile) && current.before(endDtTime)) {
				finalstatus = 4;
				System.out.println("app still running");
			}

			System.out.println("current time is " + current);
			System.out.println("expectde start time is---- " + startDTTime);
			System.out.println("start file present and conditions checked");

			if ((startFile) && (endFile)) {
				System.out.println("application completed successfully!!!!");
				finalstatus = 1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("loop exit with status as " + finalstatus);
		return finalstatus;
	}

	public List<WtsAppTab> getAppById(int applicationId) {
		String hql = "FROM WtsAppTab as trans inner join WtsTransTab  as tr where tr.application=?  ";
		return (List<WtsAppTab>) entityManager.createQuery(hql).setParameter(1, applicationId).getResultList();
	}

	public List<WtsTransTab> getFlowData(int applicationId) {
		return null;
	}

	public Timestamp startTimestamp(String name) {
		String startActTime = FileCreationTime.getStartfileCreationTime(name);
		Timestamp start = Timestamp.valueOf(startActTime);
		return start;
	}

	public Timestamp endTimestamp(String name) {
		String endActTime = FileCreationTime.getEndfileCreationTime(name);
		Timestamp end = Timestamp.valueOf(endActTime);
		return end;
	}

	public Timestamp currentTimestamp() {
		Date today = new Date();
		Timestamp current = new Timestamp(today.getTime());
		return current;

	}

	public void EtaMail(int processId) {
		List<WtsNewEtaTab> eta = etDAO.getAllCurrentEta();
		if (eta != null) {
			Iterator<WtsNewEtaTab> etaItr = eta.iterator();
			while (etaItr.hasNext()) {
				WtsNewEtaTab et = etaItr.next();
				if (et.getApplicationId() > 0) {
					WtsAppTab app = appDAO.getAppById(et.getApplicationId());
					if (app != null) {
						WtsTransTab tr = getTdyTxnByProcessIdAppId(processId,
								TreatmentDate.getInstance().getTreatmentDate(), app.getApplicationId());
						if (tr != null) {
							if (tr.getSendetaemailflag() == 0) {
								emailService.SendMailAlertNewEta(proAppMapDao.getAppMappingEmailId(processId, app.getApplicationId()));
								tr.setSendetaemailflag(1);
							}
						}
					}

				}
			}

		}
		System.out.println("new eta mail sent........");
	}
}
