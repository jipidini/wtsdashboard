package com.WTS.Dashboards.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.WTS.Dashboards.DTO.TransactionDTO;
import com.WTS.Dashboards.Entity.WtsAppMappingTab;
import com.WTS.Dashboards.Entity.WtsAppTab;
import com.WTS.Dashboards.Entity.WtsNewEtaTab;
import com.WTS.Dashboards.Entity.WtsTransTab;
import com.WTS.Dashboards.Utility.DateUtility;
import com.WTS.Dashboards.Utility.TreatmentDate;
import com.WTS.Dashboards.dao.WtsAppMappingTabDao;
import com.WTS.Dashboards.dao.WtsAppTabDao;
import com.WTS.Dashboards.dao.WtsNewEtaTabDao;
import com.WTS.Dashboards.dao.WtsProcessAppMapTabDao;
import com.WTS.Dashboards.dao.WtsTransTabDao;
import com.fasterxml.jackson.annotation.JsonFormat;

@Service
public class WtsTransTabService implements IWtsServiceInterface {

	@Autowired
	private WtsTransTabDao tranDao;
	@Autowired
	private WtsAppTabDao trapps;

	@Autowired
	private WtsAppTabDao appDAO;

	@Autowired
	private WtsNewEtaTabDao etDAO;

	@Autowired
	private EmailService emailService;

	@Autowired
	private WtsProcessAppMapTabDao proAppMapDao;

	@Autowired
	private WtsAppMappingTabDao appMapDao;

	public List<WtsTransTab> getAlltransaction() {
		return tranDao.getAlltransaction();
	}

	public synchronized boolean addTransaction(WtsTransTab trans, String name) {
		tranDao.addTransaction(trans, name);
		return true;
	}

	public List<TransactionDTO> fetchAllTxns(int processId) throws Exception {
		List<TransactionDTO> finalDTOList = new ArrayList<TransactionDTO>();
		List<WtsTransTab> finalList = new ArrayList<WtsTransTab>();
		WtsTransTab todayProTxn = this.getTdyTxnByProcessId(processId, TreatmentDate.getInstance().getTreatmentDate());
		if (todayProTxn != null) {

			finalList.add(todayProTxn);
			this.updateTransactionModifiedDetail(todayProTxn);

			List<WtsAppTab> apps = appDAO.getAllAppsByProcess(processId);
			if (apps != null) {
				Iterator<WtsAppTab> appssItr = apps.iterator();
				while (appssItr.hasNext()) {
					WtsAppTab app = (WtsAppTab) appssItr.next();
					if (app != null && DateUtility
							.isAfterNow(proAppMapDao.getAppMappingStartTime(processId, app.getApplicationId()))) {
						WtsTransTab newAppTrans = new WtsTransTab();
						newAppTrans.setProcessId(processId);
						newAppTrans.setApplicationId(app.getApplicationId());
						String appNam = app.getName();
						WtsTransTab existingApp = this.getTransactionByAppIdProId(app.getApplicationId(), processId,
								TreatmentDate.getInstance().getTreatmentDate());
						if (existingApp != null) {
							System.out.println("existing app TXn..");
							this.updateTransactionModifiedDetail(existingApp);
							finalList.add(existingApp);
						} else {
							this.addTransaction(newAppTrans, appNam);
							finalList.add(newAppTrans);
						}

					}

				}
			}
			Iterator<WtsTransTab> trnItr = finalList.iterator();
			while (trnItr.hasNext()) {
				WtsTransTab wtsTransTab = (WtsTransTab) trnItr.next();
				finalDTOList.add(this.convertToDTO(wtsTransTab));
			}

		} else {

			WtsTransTab newTrans = new WtsTransTab();
			newTrans.setProcessId(processId);
			finalList.add(newTrans);
			List<WtsAppTab> apps = appDAO.getAllAppsByProcess(processId);
			if (apps != null) {
				Iterator<WtsAppTab> appssItr = apps.iterator();
				while (appssItr.hasNext()) {
					WtsAppTab app = (WtsAppTab) appssItr.next();
					if (app != null && DateUtility
							.isAfterNow(proAppMapDao.getAppMappingStartTime(processId, app.getApplicationId()))) {
						WtsTransTab newAppTrans = new WtsTransTab();
						newAppTrans.setProcessId(processId);
						newAppTrans.setApplicationId(app.getApplicationId());
						finalList.add(newAppTrans);
					}

				}
			}

			Iterator<WtsTransTab> trnItr = finalList.iterator();
			while (trnItr.hasNext()) {
				WtsTransTab wtsTransTab = (WtsTransTab) trnItr.next();
				this.addProcessTransaction(wtsTransTab);
				finalDTOList.add(this.convertToDTO(wtsTransTab));
			}

		}

		return finalDTOList;
	}

	public TransactionDTO convertToDTO(WtsTransTab dbObj) {
		TransactionDTO dtoObj = null;
		if (dbObj != null) {
			dtoObj = new TransactionDTO();
			dtoObj.setTransactionId(dbObj.getTransactionId());
			dtoObj.setProcessId(dbObj.getProcessId());
			dtoObj.setParentId(dbObj.getParentId());

			dtoObj.setChildId(dbObj.getChildId());

			dtoObj.setApplicationId(dbObj.getApplicationId());

			dtoObj.setEventDate(dbObj.getEventDate());

			dtoObj.setStartTransaction(dbObj.getStartTransaction());
			dtoObj.setEndTransaction(dbObj.getEndTransaction());
			dtoObj.setStatusId(dbObj.getStatusId());
			dtoObj.setSendemailflag(dbObj.getSendemailflag());
			dtoObj.setSendetaemailflag(dbObj.getSendetaemailflag());
//			dtoObj.setProcessStatus(processStatus);
//			dtoObj.setAppButtonStatus(appButtonStatus);
		}
		return dtoObj;
	}

	public List<TransactionDTO> fetchAllChildTxns(int parentId, int processId, boolean mainpageNav) throws Exception {
		List<WtsTransTab> finalList = new ArrayList<WtsTransTab>();
		List<TransactionDTO> finalDTOList = new ArrayList<TransactionDTO>();
		WtsTransTab todayParentTxn = this.getTdyTxnByParentId(parentId, processId,
				TreatmentDate.getInstance().getTreatmentDate());
		if (todayParentTxn != null) {

			finalList.add(todayParentTxn);
			this.updateChildTransactionModifiedDetail(todayParentTxn, mainpageNav);

			List<WtsAppMappingTab> mappings = appMapDao.getAllAppMappingsByParent(parentId, processId);
			if (mappings != null) {
				Iterator<WtsAppMappingTab> appssItr = mappings.iterator();
				while (appssItr.hasNext()) {
					WtsAppMappingTab app = (WtsAppMappingTab) appssItr.next();
					if (app != null && DateUtility.isAfterNow(app.getStartTime())) {
						WtsTransTab newAppTrans = new WtsTransTab();
						newAppTrans.setProcessId(processId);
						newAppTrans.setParentId(app.getParentId());
						newAppTrans.setChildId(app.getChildId());
						String appNam = app.getName();
						WtsTransTab existingApp = this.getTransactionByParentChildId(app.getChildId(),
								app.getParentId(), processId, TreatmentDate.getInstance().getTreatmentDate());
						if (existingApp != null) {
							System.out.println("existing app TXn..");
							this.updateChildTransactionModifiedDetail(existingApp, mainpageNav);
							finalList.add(existingApp);
						} else {
							this.addTransaction(newAppTrans, appNam);
							finalList.add(newAppTrans);
						}

					}

				}
			}
			
			Iterator<WtsTransTab> trnItr = finalList.iterator();
			while (trnItr.hasNext()) {
				WtsTransTab wtsTransTab = (WtsTransTab) trnItr.next();
				finalDTOList.add(this.convertToDTO(wtsTransTab));
			}

		} else {

			WtsTransTab newTrans = new WtsTransTab();
			newTrans.setProcessId(processId);
			newTrans.setParentId(parentId);
			finalList.add(newTrans);
			List<WtsAppMappingTab> mappings = appMapDao.getAllAppMappingsByParent(parentId, processId);
			if (mappings != null) {
				Iterator<WtsAppMappingTab> appssItr = mappings.iterator();
				while (appssItr.hasNext()) {
					WtsAppMappingTab app = (WtsAppMappingTab) appssItr.next();
					if (app != null && DateUtility.isAfterNow(app.getStartTime())) {
						WtsTransTab newAppTrans = new WtsTransTab();
						newAppTrans.setProcessId(processId);
						newAppTrans.setParentId(app.getParentId());
						newAppTrans.setChildId(app.getChildId());
						finalList.add(newAppTrans);
					}

				}
			}

			Iterator<WtsTransTab> trnItr = finalList.iterator();
			while (trnItr.hasNext()) {
				WtsTransTab wtsTransTab = (WtsTransTab) trnItr.next();
				this.addProcessTransaction(wtsTransTab);
				finalDTOList.add(this.convertToDTO(wtsTransTab));
			}

		}

		return finalDTOList;
	}

	public synchronized boolean addProcessTransaction(WtsTransTab trans) {

		tranDao.addProcessTransaction(trans);
		return true;
	}

	public void updateTransaction(WtsTransTab trans) {
		System.out.println("Mein hu problem");
		tranDao.updateTransaction(trans);

	}

	public void deleteTransaction(int transId) {
		tranDao.deleteTransaction(transId);

	}

	public WtsTransTab getTransactionById(int transId) {
		// System.out.println("Kyu huaa call tu");
		WtsTransTab tr = (WtsTransTab) tranDao.getTransactionById(transId);
		return tr;
	}

	public void updateTransactionModifiedDetail(WtsTransTab trans) throws Exception {
		System.out.println("updateTransactionModifiedDetail(WtsTransTab trans)---- mene kiya h");
		tranDao.updateTransactionModifiedDetail(trans);
	}

	public void updateChildTransactionModifiedDetail(WtsTransTab trans, boolean mainpageNav) throws Exception {
		System.out.println("updateChildTransactionModifiedDetail(WtsTransTab trans)");
		tranDao.updateChildTransactionModifiedDetail(trans, mainpageNav);
	}

	public WtsTransTab getTdyTxnByProcessId(int processId, String trtDt) {
		System.out.println("getTransactionByProcessId-huaa");
		WtsTransTab tr = tranDao.getTdyTxnByProcessId(processId, trtDt);
		return tr;
	}

	public WtsTransTab getTdyTxnByParentId(int parentId, int processId, String trtDt) {
		WtsTransTab tr = tranDao.getTdyTxnByParentId(parentId, processId, trtDt);
		return tr;
	}

	public WtsTransTab getTransactionByAppIdProId(int appId, int proId, String treatDt) {
		WtsTransTab tr = tranDao.getTransactionByAppIdProId(appId, proId, treatDt);
		return tr;
	}

	public WtsTransTab getTransactionByParentChildId(int childId, int parentId, int processId, String treatDt) {
		WtsTransTab tr = tranDao.getTransactionByParentChildId(childId, parentId, processId, treatDt);
		return tr;
	}

	public void updateTransactionByProcessId(WtsTransTab trans) {
		tranDao.updateTransaction(trans);

	}

	public List<WtsTransTab> gettranId(int processId) {
		List<WtsTransTab> ls = tranDao.gettranId(processId);
		return ls;
	}

	public List<WtsAppTab> getAppById(int applicationId) {
		System.out.println("getTransactionByapplicationIds");
		@SuppressWarnings("unchecked")
		List<WtsAppTab> tr = (List<WtsAppTab>) trapps.getAppById(applicationId);
		return tr;
	}

	public List<WtsTransTab> getFlowData(int applicationId) {
		// TODO Auto-generated method stub
		return null;
	}

	public WtsTransTab getTransactionById(Object object) {
		// TODO Auto-generated method stub
		WtsTransTab trs = tranDao.getTransactionById((int) object);
		return null;
	}

	public void EtaMail(int processId) {
		tranDao.EtaMail(processId);
	}
}
