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

import com.WTS.Dashboards.Entity.WtsAppTab;
import com.WTS.Dashboards.Entity.WtsTransTab;
import com.WTS.Dashboards.Utility.EmailUtil;
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
	
	public WtsTransTab getTransactionById(int transactionId) {
		return entityManager.find(WtsTransTab.class,transactionId);
	}
	
//	public WtsTransTab getTransactionByProcessId(int processId) {
//		System.out.println("getTransactionByProcessId(int processId)");
//		return entityManager.find(WtsTransTab.class,processId);
//	}
//	
	@SuppressWarnings("unchecked")
	
	public List<WtsTransTab> getAlltransaction() {
		String hql = "FROM WtsTransTab as tran ORDER BY tran.transactionId";
		return (List<WtsTransTab>) entityManager.createQuery(hql).getResultList();
	}


	public void addTransaction(WtsTransTab transaction, String name) {
		try {
			transaction.setEventDate(TreatmentDate.getInstance().getTreatmentDate());
	
			if(FileCreationTime.getStartfileCreationTime(name)!=null)
			transaction.setStartTransaction(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(FileCreationTime.getStartfileCreationTime(name)));
			if(FileCreationTime.getEndfileCreationTime(name)!=null)
				transaction.setEndTransaction(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(FileCreationTime.getEndfileCreationTime(name)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//transaction.setStatusId(FileCreationTime.checkFileStatus());
		
			entityManager.persist(transaction);
			entityManager.flush();
		 
	}
	
	/*public void addTransaction(WtsTransTab transaction) {
		try {
			transaction.setEventDate(TreatmentDate.getInstance().getTreatmentDate());
			if(FileCreationTime.getStartfileCreationTime(name)!=null)
			transaction.setStartTransaction(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(FileCreationTime.getStartfileCreationTime(name)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		transaction.setStatusId(FileCreationTime.checkFileStatus());
		
			entityManager.persist(transaction);
			entityManager.flush();
		 
	}
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
	public boolean transactionExists(int transactionId) {
		System.out.println("Why this why this ");
		
		String hql = "FROM WtsTransTab as trans WHERE trans.transaction_id= ? ";
		int count = entityManager.createQuery(hql).setParameter(1, transactionId).getResultList().size();
		entityManager.flush();
		return count > 0 ? true : false;	
	}
	*/
	public WtsTransTab getTdyTxnByProcessId(int processId, String trtDt)
	{
		String hql="from WtsTransTab WHERE processId=? and eventDate= ? AND application_id IS null and batch_id IS null";
	
	Query qry=entityManager.createQuery(hql);
	qry.setParameter(1,processId);
	qry.setParameter(2,trtDt);
	if(qry.getResultList()!=null && !qry.getResultList().isEmpty())
		return (WtsTransTab)qry.getResultList().get(0);
	else
	return null;
				
	}
	
	public WtsTransTab getTransactionByAppIdProId(int appId,int processid, String treatDt)
	{
		String hql="from WtsTransTab WHERE processId=? AND applicationId=? AND eventDate= ?";
		Query qry=entityManager.createQuery(hql);
		qry.setParameter(1,processid);
		qry.setParameter(2,appId);
		qry.setParameter(3,treatDt);
		if(qry.getResultList()!=null && !qry.getResultList().isEmpty())
			return (WtsTransTab)qry.getResultList().get(0);
		else
		return null;
					
	}
	
	public WtsTransTab getTransactionByAppIdProIdBatId(int appId,int processid,int batchId, Date treatDt)
	{
		String hql="from WtsTransTab WHERE processId=? AND applicationId=? AND batchId=? AND eventDate= ?";
		Query qry=entityManager.createQuery(hql);
		qry.setParameter(1,processid);
		qry.setParameter(2,appId);
		qry.setParameter(3,batchId);
		qry.setParameter(4,treatDt);
		if(qry.getResultList()!=null && !qry.getResultList().isEmpty())
			return (WtsTransTab)qry.getResultList().get(0);
		else
		return null;
					
	}
	public WtsTransTab getTdyTxnByProcessIdAppId(int processId, String trtDt,int appId)
	{
		String hql="from WtsTransTab WHERE processId=? and eventDate= ? AND application_id= ? and batch_id IS null";
	
	Query qry=entityManager.createQuery(hql);
	qry.setParameter(1,processId);
	qry.setParameter(2,trtDt);
	qry.setParameter(3,appId);
	if(qry.getResultList()!=null && !qry.getResultList().isEmpty())
		return (WtsTransTab)qry.getResultList().get(0);
	else
	return null;
				
	}
	public boolean transactionExistsbyProcessId(int process)
	{
    	System.out.println("boolean transactionExistsbyProcessId(int Id)");
	String hql="From WtsTranTab as trans WHERE trans.processId=? ";
	int count = entityManager.createQuery(hql).setParameter(1,process).getResultList().size();
	entityManager.flush();
	return count > 0 ? true : false;
	}
	
   public List <WtsTransTab> gettranId(int processId)
   {
	   System.out.println("processId"+processId);
	   String hql="FROM WtsTransTab as trans WHERE trans.processId=:proc";
	   
	 List <WtsTransTab> ls=entityManager.createQuery(hql).setParameter("proc",processId).getResultList();
	  
   return ls;
   }
   
   public void addProcessTransaction(WtsTransTab transaction) {

			transaction.setEventDate(TreatmentDate.getInstance().getTreatmentDate());
			
			transaction.setStatusId(0);
		
			entityManager.persist(transaction);
			entityManager.flush();
		 
	}
   
   public void updateTransactionModifiedDetail(WtsTransTab trans) throws Exception {
	   WtsTransTab transa=(WtsTransTab)trans;
		  
	   Timestamp startDTTime=null;
	   Timestamp endDtTime=null;
	   //APPLICATION
	   if(transa.getApplicationId()>0) {
		  WtsAppTab appln= appDAO.getAppById(transa.getApplicationId());
		  String name= appln.getName();
		  startDTTime=appln.getStartTime();
		  endDtTime=appln.getEndTime();
		  int curSeq=appln.getSequence();
		  int status=getFileStatus(startDTTime,endDtTime,name);
		  System.out.println("getFileStatus function checked and status set");
		  
		   transa.setStatusId(status);
		   if(FileCreationTime.getStartfileCreationTime(name)!=null)
			   transa.setStartTransaction(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(FileCreationTime.getStartfileCreationTime(name)));
		   if(status==1){
			   if(FileCreationTime.getEndfileCreationTime(name)!=null)
				   transa.setEndTransaction(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(FileCreationTime.getEndfileCreationTime(name)));
				   System.out.println("start file set time" +new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(FileCreationTime.getEndfileCreationTime(name)));
			}
		   if(status==2){
			   	if(FileCreationTime.getFailfileCreationTime(name)!=null)
			   		transa.setEndTransaction(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(FileCreationTime.getFailfileCreationTime(name)));
		   }
		   boolean isprevRed=false;
		   List <WtsAppTab> apps=appDAO.getAllAppsByProcess(transa.getProcessId());
		   if(apps!=null) {
				 Iterator<WtsAppTab> appssItr=apps.iterator();
				 while (appssItr.hasNext()) {
					 WtsAppTab app = (WtsAppTab) appssItr.next();
					 if(app.getSequence()<curSeq) {
						 WtsTransTab apptrans= getTdyTxnByProcessIdAppId(transa.getProcessId(),TreatmentDate.getInstance().getTreatmentDate(),app.getApplicationId());
						 if(apptrans!=null && apptrans.getStatusId()== 2) {
							 transa.setStatusId(2);
							 //should be replaced with real reporting configured emails for the app in place of hardcoded values
							 new EmailUtil().sendSimpleMessage("behera.deb@gmail.com", "Application-"+app.getName()+"-STATUS RED!", "Process: "+processDAO.getProcessById(transa.getProcessId()).getName()
									 + " Application:- "+app.getName()+" STATUS RED!!");
						 }
					 }
				 }
		   }
		   
		   //PROCESS
	   } else if(transa.getApplicationId()==0) {
			   Date startTxnTime=null;
				Date EndTxnTime=null;
				int status=0;
				List <WtsAppTab> apps=appDAO.getAllAppsByProcess(transa.getProcessId());
				
				 if(apps!=null) {
					 Iterator<WtsAppTab> appssItr=apps.iterator();
					 int seq=0;
					 while (appssItr.hasNext()) {
						WtsAppTab wtsAppTab = (WtsAppTab) appssItr.next();
						if (wtsAppTab.getSequence()==1) {
							 startTxnTime=wtsAppTab.getStartTime();
							 status=4;
						}
						if(wtsAppTab.getSequence()> seq) {
							seq=wtsAppTab.getSequence();
							EndTxnTime=wtsAppTab.getEndTime();
							 String name= wtsAppTab.getName();
							  startDTTime=wtsAppTab.getStartTime();
							  endDtTime=wtsAppTab.getEndTime();
							 status=getFileStatus(startDTTime,endDtTime,name);
							  
						}
						
					}
				 }
				 transa.setStartTransaction(startTxnTime);
				 transa.setEndTransaction(EndTxnTime);
				 transa.setStatusId(status);
				 
		   }
	  
	   
	   entityManager.flush();
	
   }
   
   private int getFileStatus(Timestamp startDTTime, Timestamp endDtTime,String name) {
	   int finalstatus=0;
	   try{
		    System.out.println("getFileStatus Loop entered");
		  
			boolean startFile= FileCreationTime.stratFileExist(name);
			boolean endFile = FileCreationTime.endFileExist(name);
			boolean failFile= FileCreationTime.failFileExist(name);
			
			Timestamp current= currentTimestamp();
		 if( (!startFile) && (!endFile) && (!failFile)){
				   finalstatus=0;
				   System.out.println("not started");
			  }
		 
		 else if((!startFile) && (!endFile) && (failFile)){	
		 		
				finalstatus=2;
}
			  
		 else if((startFile) && (!endFile) && (failFile)){	
				 		
								finalstatus=2;
		 }
		 
		else  if((startFile) && (!endFile) && (current.after(endDtTime)) ) {
				 		 
				 		 				finalstatus=3;
				 		 				System.out.println("application completion delayed");
	   }
		 else if((startFile) && (!endFile) && current.before(endDtTime)){
				 		finalstatus=4;
				 		System.out.println("app still running");
				  }
					 
				  
				  System.out.println("current time is "+current);
				  System.out.println("expectde start time is---- "+startDTTime);
				  System.out.println("start file present and conditions checked");
	
			  
			  
		  if((startFile) && (endFile)){
					  System.out.println("application completed successfully!!!!");
					  finalstatus=1;
			  }	
		 
	    } 
	   catch (Exception e){
	        e.printStackTrace();
	    }
	   System.out.println("loop exit with status as "+finalstatus);
	   return finalstatus;
  }

   
public List<WtsAppTab> getAppById(int applicationId) {
	String hql="FROM WtsAppTab as trans inner join WtsTransTab  as tr where tr.application=?  ";
	return(List<WtsAppTab>) entityManager.createQuery(hql).setParameter(1,applicationId).getResultList();
		}


public List<WtsTransTab> getFlowData(int applicationId) {
	return null;
}

	public Timestamp startTimestamp(String name){
		String startActTime = FileCreationTime.getStartfileCreationTime(name);
		Timestamp start= Timestamp.valueOf(startActTime);
		return start;
	}
	
	public Timestamp endTimestamp(String name){
		String endActTime= FileCreationTime.getEndfileCreationTime(name);
		Timestamp end= Timestamp.valueOf(endActTime);
		return end;
	}
	
	public Timestamp currentTimestamp(){
		Date today= new Date();
		Timestamp current = new Timestamp(today.getTime());
		return current;
		
	}
}
