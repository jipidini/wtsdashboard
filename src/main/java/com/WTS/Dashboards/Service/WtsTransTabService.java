package com.WTS.Dashboards.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.WTS.Dashboards.Entity.WtsAppTab;
import com.WTS.Dashboards.Entity.WtsTransTab;
import com.WTS.Dashboards.Utility.DateUtility;
import com.WTS.Dashboards.Utility.TreatmentDate;
import com.WTS.Dashboards.dao.WtsAppTabDao;
import com.WTS.Dashboards.dao.WtsTransTabDao;

@Service
public class WtsTransTabService implements IWtsServiceInterface {

	@Autowired
	private WtsTransTabDao tranDao;
	@Autowired
	private WtsAppTabDao trapps;
	
	@Autowired
	private WtsAppTabDao appDAO;
	
	public List<WtsTransTab> getAlltransaction() {
		return tranDao.getAlltransaction();
	}

	
	public synchronized boolean addTransaction(WtsTransTab trans, String name) {
				tranDao.addTransaction(trans , name);
				return true;
		}
	
	
	public List <WtsTransTab> fetchAllTxns(int processId) throws Exception{
		List <WtsTransTab> finalList=new ArrayList<WtsTransTab>();;
		WtsTransTab todayProTxn=this.getTdyTxnByProcessId(processId,TreatmentDate.getInstance().getTreatmentDate());
		 if(todayProTxn!=null) {
	
				finalList.add(todayProTxn);
				this.updateTransactionModifiedDetail(todayProTxn);
				
				List <WtsAppTab> apps=appDAO.getAllAppsByProcess(processId);
				 if(apps!=null) {
					 Iterator<WtsAppTab> appssItr=apps.iterator();
					 while (appssItr.hasNext()) {
						 WtsAppTab app = (WtsAppTab) appssItr.next();
						 if(app!=null && DateUtility.isAfterNow(app.getStartTime())) {
							 WtsTransTab newAppTrans= new WtsTransTab();
							 newAppTrans.setProcessId(processId);
							 newAppTrans.setApplicationId(app.getApplicationId());
							 String appNam=app.getName();
							WtsTransTab existingApp=this.getTransactionByAppIdProId(app.getApplicationId(), processId,TreatmentDate.getInstance().getTreatmentDate());
							if(existingApp!=null) {
								System.out.println("existing app TXn..");
									this.updateTransactionModifiedDetail(existingApp);
									finalList.add(existingApp);
							}else {
								this.addTransaction(newAppTrans,appNam);
								finalList.add(newAppTrans);
							}
								
	
						 }
						
					} 
				 }
			
		 }else {
		
			 WtsTransTab newTrans= new WtsTransTab();
			 newTrans.setProcessId(processId);
			 finalList.add(newTrans);
			 List <WtsAppTab> apps=appDAO.getAllAppsByProcess(processId);
			 if(apps!=null) {
				 Iterator<WtsAppTab> appssItr=apps.iterator();
				 while (appssItr.hasNext()) {
					 WtsAppTab app = (WtsAppTab) appssItr.next();
					 if(app!=null && DateUtility.isAfterNow(app.getStartTime())) {
						 WtsTransTab newAppTrans= new WtsTransTab();
						 newAppTrans.setProcessId(processId);
						 newAppTrans.setApplicationId(app.getApplicationId());
						 finalList.add(newAppTrans);
					 }
					
				} 
			 }
		 
			 Iterator<WtsTransTab> trnItr=finalList.iterator();
			 while (trnItr.hasNext()) {
				WtsTransTab wtsTransTab = (WtsTransTab) trnItr.next();
				this.addProcessTransaction(wtsTransTab);
			}
		 
		 
		 
		 }
	 return finalList;
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
		//System.out.println("Kyu huaa call tu");
		WtsTransTab tr= (WtsTransTab) tranDao.getTransactionById(transId);
		return tr;	
	}

	
	public void updateTransactionModifiedDetail(WtsTransTab trans) throws Exception {
		System.out.println("updateTransactionModifiedDetail(WtsTransTab trans)---- mene kiya h");
		tranDao.updateTransactionModifiedDetail(trans);
	}

	
	public WtsTransTab getTdyTxnByProcessId(int processId, String trtDt) {
		System.out.println("getTransactionByProcessId-huaa");
		WtsTransTab tr=  tranDao.getTdyTxnByProcessId(processId,trtDt);
		return tr;
	}
	
	public WtsTransTab getTransactionByAppIdProId(int appId,int proId, String treatDt) {
		WtsTransTab tr= tranDao.getTransactionByAppIdProId(appId,proId,treatDt);
		return tr;
	}
	
	public void updateTransactionByProcessId(WtsTransTab trans) {
		tranDao.updateTransaction(trans);
		
	}

	
	public List <WtsTransTab> gettranId(int processId) {
		List <WtsTransTab> ls=tranDao.gettranId(processId);
		return ls;
	}
	public List<WtsAppTab> getAppById(int applicationId) {
		System.out.println("getTransactionByapplicationIds");
		@SuppressWarnings("unchecked")
		List<WtsAppTab> tr= (List<WtsAppTab>) trapps.getAppById(applicationId);
		return tr;
	}

	
	public List<WtsTransTab> getFlowData(int applicationId) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public WtsTransTab getTransactionById(Object object) {
		// TODO Auto-generated method stub
		WtsTransTab trs=tranDao.getTransactionById((int)object);
		return null;
	}
}
