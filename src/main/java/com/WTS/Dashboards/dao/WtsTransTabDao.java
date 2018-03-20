package com.WTS.Dashboards.dao;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
		String hql = "FROM WtsTransTab as tran ORDER BY tran.transaction_id";
		return (List<WtsTransTab>) entityManager.createQuery(hql).getResultList();
	}


	public void addTransaction(WtsTransTab transaction,String name) {
		try {
			transaction.setEventDate(TreatmentDate.getInstance().getTreatmentDate());
			if(FileCreationTime.getStartfileCreationTime(name)!=null)
			transaction.setStartTransaction(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(FileCreationTime.getStartfileCreationTime(name)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		transaction.setStatusId(FileCreationTime.checkFileStatus(name));
		
			entityManager.persist(transaction);
			entityManager.flush();
		 
	}

	public void addProcessTransaction(WtsTransTab transaction) {
//		try {
			transaction.setEventDate(TreatmentDate.getInstance().getTreatmentDate());
//			if(FileCreationTime.getStartfileCreationTime(name)!=null)
//			transaction.setStartTransaction(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(FileCreationTime.getStartfileCreationTime(name)));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		transaction.setStatusId(0);
		
			entityManager.persist(transaction);
			entityManager.flush();
		 
	}
	
	public void updateTransaction(WtsTransTab transaction) {
		WtsTransTab tran = getTransactionById(transaction.getTransactionId());
		System.out.println("Check---------> by transactionID");
		tran.setEventDate(transaction.getEventDate());
		tran.setStatusId(transaction.getStatusId());
		entityManager.flush();
	}

	
	public void deleteTransaction(int transactionId) {
	entityManager.remove(getTransactionById(transactionId));
//		entityManager.remove(getTransactionByProcessId(transactionId));
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
   
   
//	public void updateTransactionModifiedDetail(WtsTransTab trans) throws Exception {
//		// TODO Auto-generated method stub
//		
//		WtsTransTab transa=(WtsTransTab)trans;
//		System.out.println("Dao----- FileCreationTimeStartTime"+FileCreationTime.LastModifiedtime);
//		if((FileCreationTime.LastModifiedtime==null ) || (FileCreationTime.ActualStarttime==null))
//		{
//			transa.setStatusId(FileCreationTime.status);
////			transa.setStartTransaction(Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format("00-00-00 00:00:00")));
////			transa.setEndTransaction(Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format("00-00-00 00:00:00")));
//			entityManager.flush();
//		}
//		else
//		{
//        transa.setStartTransaction(Timestamp.valueOf(FileCreationTime.ActualStarttime));
//		System.out.println("FileCreationTime.ActualStarttime"+FileCreationTime.ActualStarttime);
//		transa.setEndTransaction(Timestamp.valueOf(FileCreationTime.LastModifiedtime));
//		System.out.println("FileCreationTime.LastModifiedtime"+FileCreationTime.LastModifiedtime);
//		transa.setStatusId(FileCreationTime.status);
//		entityManager.flush();
//		}
////		System.out.println("FileCreationTime.getTriggerFileStatus"+FileCreationTime.getTriggerFileStatus());
////		System.out.println("FileCreationTime.getTriggerFileStatus()"+FileCreationTime.getTriggerFileStatus());
//		System.out.println("EventDate------------->"+transa.getEventDate());
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		Calendar calobj = Calendar.getInstance();
//		System.out.println("eventdate format"+df.format(transa.getEventDate()));
//		System.out.println("calobj.getTime()"+df.format(calobj.getTime()));
////		System.out.println("df.format(transa.getEventDate()).equals(calobj.getTime())"+df.format(transa.getEventDate()).equals(calobj.getTime()));
////		if(df.format(transa.getEventDate()).equals(df.format(calobj.getTime())))
////	{
////		System.out.println("Working bhai mere");
////	transa.setStatusId(1);
////	entityManager.flush();
////	}
//		
//	}
   
   

//	
//	public void updateTransactionByProcessId(WtsTransTab transaction) throws Exception {
//		// TODO Auto-generated method stub
//		WtsTransTab trans=(WtsTransTab) getTransactionByProcessId(transaction.getProcessId());
//		System.out.println("new WtsTransTabDao().updateTransactionModifiedDetail(trans);----> working");
//		(new WtsTransTabDao()).updateTransactionModifiedDetail(trans);
//	}
//
//	
	

   public void updateTransactionModifiedDetail(WtsTransTab trans) throws Exception {
	   WtsTransTab transa=(WtsTransTab)trans;
	  
	   Timestamp startDTTime=null;
	   Timestamp endDtTime=null;
	   if(transa.getApplicationId()>0) {
		  WtsAppTab appln= appDAO.getAppById(transa.getApplicationId());
		  String name= appln.getName();
		  startDTTime=appln.getStartTime();
		  endDtTime=appln.getEndTime();
		  int status=getFileStatus(startDTTime,endDtTime,name);
		   transa.setStatusId(status);
		   if(status ==1) {
			   if(FileCreationTime.getEndfileCreationTime(name)!=null)
			   transa.setEndTransaction(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(FileCreationTime.getEndfileCreationTime(name)));
		   }else if(status ==2) {
			   if(FileCreationTime.getFailfileCreationTime(name)!=null)
			   transa.setEndTransaction(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(FileCreationTime.getFailfileCreationTime(name)));
		   }else if (status==4) {
			   if(FileCreationTime.getStartfileCreationTime(name)!=null)
				   transa.setStartTransaction(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(FileCreationTime.getStartfileCreationTime(name)));
		   }
	   }else if(transa.getApplicationId()==0) {
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
	   try {
			

			  String starttime = new SimpleDateFormat("HH:mm:ss").format(startDTTime);
			  String endtime = new SimpleDateFormat("HH:mm:ss").format(endDtTime);
			  String startTimeParse[] = starttime.split(":");
			  String endTimeParse[] = endtime.split(":");
			  int firstHour = Integer.parseInt(startTimeParse[0]);
			  int firstMinute = Integer.parseInt(startTimeParse[1]);
			  int firstSec = Integer.parseInt(startTimeParse[2]);
			  int secondHour = Integer.parseInt(endTimeParse[0]);
			  int secondMinute = Integer.parseInt(endTimeParse[1]);
			  int secondSec = Integer.parseInt(endTimeParse[2]);
			  
			  Calendar calstart=Calendar.getInstance();
			  calstart.set(Calendar.HOUR, firstHour);
			  calstart.set(Calendar.MINUTE, firstMinute);
			  calstart.set(Calendar.SECOND, firstSec);
			  
			  Calendar calend=Calendar.getInstance();
			  calend.set(Calendar.HOUR, secondHour);
			  calend.set(Calendar.MINUTE, secondMinute);
			  calend.set(Calendar.SECOND, secondSec);
			  
			  Calendar calNow=Calendar.getInstance();
			  if(calNow.getTimeInMillis()<calstart.getTimeInMillis()) {
				  //yet to start
				  finalstatus= 0;
			  }
			  
			  if(calNow.getTimeInMillis()>calstart.getTimeInMillis() && calNow.getTimeInMillis()<calend.getTimeInMillis()) {
				//inprogress
				  finalstatus= FileCreationTime.checkFileStatus(name);
			  }
			  
			  if(calNow.getTimeInMillis()>calstart.getTimeInMillis() && calNow.getTimeInMillis()>calend.getTimeInMillis()) {
				  //fail/complete/in progress
				  finalstatus= FileCreationTime.checkFileStatus(name);
				  if(finalstatus==4) {
					  finalstatus=3;	 
				  }else if(finalstatus==0) {
					  finalstatus=2;	 
				  }
			  }
			  
	    } catch (Exception e){
	        e.printStackTrace();
	    }
	   
	   return finalstatus;
   }

public List<WtsAppTab> getAppById(int applicationId) {
	String hql="FROM WtsAppTab as trans inner join WtsTransTab  as tr where tr.application=?  ";
	return(List<WtsAppTab>) entityManager.createQuery(hql).setParameter(1,applicationId).getResultList();
		}


public List<WtsTransTab> getFlowData(int applicationId) {
	// TODO Auto-generated method stub
	return null;
}

	
	
}
