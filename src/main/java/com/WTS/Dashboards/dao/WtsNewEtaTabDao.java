package com.WTS.Dashboards.dao;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.WTS.Dashboards.Entity.WtsAppTab;
import com.WTS.Dashboards.Entity.WtsNewEtaTab;
import com.WTS.Dashboards.Entity.WtsProcessTab;
import com.WTS.Dashboards.Entity.WtsTransTab;
import com.WTS.Dashboards.Utility.DateUtility;
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
	
	public WtsNewEtaTab getEtaById(int newEtaId) {
		return entityManager.find(WtsNewEtaTab.class, newEtaId);
	}
	
	
	public void addNewEta(WtsNewEtaTab newEta) {
		
		entityManager.persist(newEta);
		   entityManager.flush();
		
		
	}
	
	public void updateNewEta(WtsNewEtaTab newEta){
		WtsNewEtaTab eta= getEtaById(newEta.getEtaId());
		eta.setApplicationId(newEta.getApplicationId());
		eta.setProcessId(newEta.getProcessId());
		eta.setNewEtaStartTransaction(newEta.getNewEtaStartTransaction());
		eta.setNewEtaEndTransaction(newEta.getNewEtaEndTransaction());
		eta.setEventDate(TreatmentDate.getInstance().getTreatmentDate());
		
	}
	
	public void deleteNewEta(int newEtaId) {
		entityManager.remove(getEtaById(newEtaId));
		
	}
	
  public int isProblemFlag(){
	  int flag=0;
	  String hql="select problemFlag FROM WtsNewEtaTab where eventDate= current_date";
	int entry = entityManager.createQuery(hql).getResultList().size();
	if (entry != 0){
		 flag =0;
	}
	else {
		 flag=1;
	}
	  return flag;
  }
  
  
  public void newEtaCalculation(WtsAppTab app , int processId) throws ParseException {
	  System.out.println("start time loop entered");
	  String name= app.getName();
	  int apId=app.getApplicationId();
	  int currentSeq=app.getSequence();
	 List<WtsNewEtaTab> etaLst= new ArrayList<>();
	  Timestamp startDTTime=app.getStartTime();
	  int buf=app.getBufferTime();
	  Timestamp endDTTime=app.getEndTime();
	  startDTTime=DateUtility.addBufferTime(startDTTime,app.getBufferTime());  
	  endDTTime=DateUtility.addBufferTime(endDTTime,app.getBufferTime());
	 
	  Timestamp fileStart=null;
	  Timestamp fileEnd=null;
	  Long endDiff=Long.valueOf(0);
	  Long startDiff=Long.valueOf(0);
	  if(FileCreationTime.getStartfileCreationTime(name)!=null) {
		  fileStart= FileCreationTime.startTimestamp(name);
		  startDiff= (fileStart.getTime()-startDTTime.getTime()+buf);
	  }
	  if(FileCreationTime.getEndfileCreationTime(name)!=null) {
		  fileEnd= FileCreationTime.endTimestamp(name);
		 endDiff= (fileEnd.getTime()-endDTTime.getTime()+buf);
	  }
	  WtsProcessTab processObj= processDAO.getProcessById(processId);
	  Timestamp startProcessTime=null;
	  Timestamp endProcessTime=null;
	  
	  if(processObj!=null) {
		  startProcessTime=processObj.getExpectedStartTime();
		  endProcessTime=processObj.getExpectedEndTime();
	  }
	  
	  List apps =appDAO.getAllAppsByProcess(app.getProcessId());
	  //start Change case
	  if(startDiff>0) {
//	  Timestamp start= new Timestamp(StartDiff);
	 System.out.println("in start DIFF");
		 Iterator<WtsAppTab> apItr=apps.iterator();
		  while(apItr.hasNext()){
			  WtsAppTab nextApp = (WtsAppTab) apItr.next();
			  if(nextApp.getSequence()>currentSeq) {
				  WtsNewEtaTab et=getTdyETATxnByProcessIdAppID(nextApp.getApplicationId(), nextApp.getProcessId(), TreatmentDate.getInstance().getTreatmentDate());
				  if(et==null) {
					  et= new WtsNewEtaTab();
				  }
				  Timestamp startApp= nextApp.getStartTime();
				  Timestamp endDtTime= nextApp.getEndTime();
				  Long newStartL= startDiff+startApp.getTime();
				  Timestamp newStart= new Timestamp(newStartL);
				  Long newEndL= startDiff+endDtTime.getTime();
				  Timestamp newEnd= new Timestamp(newEndL);
				  et.setNewEtaEndTransaction(newEnd);  
				  et.setNewEtaStartTransaction(newStart); 
				  et.setApplicationId(nextApp.getApplicationId());
				  et.setProcessId(processId);
				  et.setEventDate(TreatmentDate.getInstance().getTreatmentDate());
				  et.setProblemFlag(0);
				  etaLst.add(et);
			  }else if(nextApp.getSequence()==currentSeq) {
				  WtsNewEtaTab et=getTdyETATxnByProcessIdAppID(nextApp.getApplicationId(), nextApp.getProcessId(), TreatmentDate.getInstance().getTreatmentDate());
				  if(et==null) {
					  et= new WtsNewEtaTab();
				  }
				  Timestamp startApp= nextApp.getStartTime();
				  Timestamp endDtTime= nextApp.getEndTime();
				  Long newStartL= startDiff+startApp.getTime();
				  Timestamp newStart= new Timestamp(newStartL);
				  Long newEndL= startDiff+endDtTime.getTime();
				  Timestamp newEnd= new Timestamp(newEndL);
				  endProcessTime=  new Timestamp(startDiff+endProcessTime.getTime());
				  et.setNewEtaEndTransaction(newEnd);  
				  et.setNewEtaStartTransaction(newStart); 
				  et.setApplicationId(nextApp.getApplicationId());
				  et.setProcessId(processId);
				  et.setEventDate(TreatmentDate.getInstance().getTreatmentDate());
				  et.setProblemFlag(1);
				  etaLst.add(et);
			  }
			 
			 
		
			  
	  }
		//update process ETA
		  WtsNewEtaTab exist_proc_eta=getTdyETATxnByProcessId(processId, TreatmentDate.getInstance().getTreatmentDate());
		  if(exist_proc_eta==null) {
			  exist_proc_eta= new WtsNewEtaTab();
		  }
		  
		  exist_proc_eta.setProcessId(processId);
		  exist_proc_eta.setEventDate(TreatmentDate.getInstance().getTreatmentDate());
		  exist_proc_eta.setProblemFlag(0);
		  exist_proc_eta.setNewEtaEndTransaction(endProcessTime);  
		  exist_proc_eta.setNewEtaStartTransaction(startProcessTime); 
		  etaLst.add(exist_proc_eta);
		  
			 
		  if(!etaLst.isEmpty()) {
			  Iterator<WtsNewEtaTab> etaItr=etaLst.iterator();
			  while (etaItr.hasNext()) {
				WtsNewEtaTab wtsNewEtaTab = (WtsNewEtaTab) etaItr.next();
				addNewEta(wtsNewEtaTab);
			}
		  }
		  
	  }else if(endDiff>0) {
		  System.out.println("in end DIFF");
			 Iterator<WtsAppTab> apIt=apps.iterator();
			  while(apIt.hasNext()){
				  WtsAppTab nextApp = (WtsAppTab) apIt.next();
				  if(nextApp.getSequence()>currentSeq) {
					  WtsNewEtaTab et=getTdyETATxnByProcessIdAppID(app.getApplicationId(), app.getProcessId(), TreatmentDate.getInstance().getTreatmentDate());
					  if(et==null) {
						  et= new WtsNewEtaTab();
					  }
					  
					  Timestamp startApp= nextApp.getStartTime();
					  Timestamp endDtTime= app.getEndTime();
					  Long newStartL= endDiff+startApp.getTime();
					  Timestamp newStart= new Timestamp(newStartL);
					  Long newEndL= endDiff+endDtTime.getTime();
					  Timestamp newEnd= new Timestamp(newEndL);
					  et.setNewEtaEndTransaction(newEnd);  
					  et.setNewEtaStartTransaction(newStart); 
					  et.setApplicationId(nextApp.getApplicationId());
					  et.setProcessId(processId);
					  et.setEventDate(TreatmentDate.getInstance().getTreatmentDate());
					  et.setProblemFlag(0);
					  etaLst.add(et);
				  }else if(nextApp.getSequence()==currentSeq) {
					  WtsNewEtaTab et=getTdyETATxnByProcessIdAppID(app.getApplicationId(), app.getProcessId(), TreatmentDate.getInstance().getTreatmentDate());
					  if(et==null) {
						  et= new WtsNewEtaTab();
					  }
					  Timestamp startApp= nextApp.getStartTime();
					  Timestamp endDtTime= app.getEndTime();
					  Long newStartL= startApp.getTime();
					  Timestamp newStart= new Timestamp(newStartL);
					  Long newEndL= endDiff+endDtTime.getTime();
					  Timestamp newEnd= new Timestamp(newEndL);
					  endProcessTime=  new Timestamp(endDiff+endProcessTime.getTime());
					  et.setNewEtaEndTransaction(newEnd);  
					  et.setNewEtaStartTransaction(newStart); 
					  et.setApplicationId(nextApp.getApplicationId());
					  et.setProcessId(processId);
					  et.setEventDate(TreatmentDate.getInstance().getTreatmentDate());
					  et.setProblemFlag(1);
					  etaLst.add(et);
				  }
				  
		  }
			//update process ETA
			  WtsNewEtaTab exist_proc_eta=getTdyETATxnByProcessId(processId, TreatmentDate.getInstance().getTreatmentDate());
			  if(exist_proc_eta==null) {
				  exist_proc_eta= new WtsNewEtaTab();
			  }
			  
			  exist_proc_eta.setProcessId(processId);
			  exist_proc_eta.setEventDate(TreatmentDate.getInstance().getTreatmentDate());
			  exist_proc_eta.setProblemFlag(0);
			  exist_proc_eta.setNewEtaEndTransaction(endProcessTime);  
			  exist_proc_eta.setNewEtaStartTransaction(startProcessTime); 
			  etaLst.add(exist_proc_eta);
				 
			  if(!etaLst.isEmpty()) {
				  Iterator<WtsNewEtaTab> etaItr=etaLst.iterator();
				  while (etaItr.hasNext()) {
					WtsNewEtaTab wtsNewEtaTab = (WtsNewEtaTab) etaItr.next();
					addNewEta(wtsNewEtaTab);
				}
			  }
			
	  }
	/*//Process ETA calculation start
	  WtsNewEtaTab eta= new WtsNewEtaTab();
		 eta= EtaProcessTime(processId);
		 etaLst.add(eta);
		 System.out.println("process ETA time added for process!!  "+processId);
		 //Process ETA calculation end
*/	
	  entityManager.flush();
		  //System.out.println("new start time is "+fileStart);
		  System.out.println("ETA set for start transations..");
  }
  
  
  private WtsNewEtaTab getTdyETATxnByProcessId(int processId, String treatmentDate) {
	  String hql="from WtsNewEtaTab WHERE processId=?  AND eventDate= ?";
		Query qry=entityManager.createQuery(hql);
		qry.setParameter(1,processId);
		qry.setParameter(2,treatmentDate);
		if(qry.getResultList()!=null && !qry.getResultList().isEmpty())
			return (WtsNewEtaTab)qry.getResultList().get(0);
		else
		return null;
				
}


public WtsNewEtaTab getTdyETATxnByProcessIdAppID(int appId,int processid, String treatDt)
	{
		
		String hql="from WtsNewEtaTab WHERE processId=? AND applicationId=? AND eventDate= ?";
		Query qry=entityManager.createQuery(hql);
		qry.setParameter(1,processid);
		qry.setParameter(2,appId);
		qry.setParameter(3,treatDt);
		if(qry.getResultList()!=null && !qry.getResultList().isEmpty())
			return (WtsNewEtaTab)qry.getResultList().get(0);
		else
		return null;
				
	}
  
 /* public Timestamp newEtaEndCalculation(WtsAppTab app,Date newEndDTTime,int applicationId, int processId){
	  String name= app.getName();
	  int apId=app.getApplicationId();
	  WtsNewEtaTab et= new WtsNewEtaTab();
	  Timestamp startApp= app.getStartTime();
	  Timestamp endDtTime= app.getEndTime();
	  Date fileEnd= newEndDTTime;
	  Long endDiff= (fileEnd.getTime()-endDtTime.getTime());
//		Timestamp end= new Timestamp(endDiff);
	  String hql= "From WtsAppTab as apn where apn.applicationId>=?";
		List<WtsAppTab> ap= (List<WtsAppTab>) entityManager.createQuery(hql).setParameter(1,apId).getResultList();
		 Iterator<WtsAppTab> apIt=ap.iterator();
		  while(apIt.hasNext()){
			  WtsAppTab apln = (WtsAppTab) apIt.next();
			  et.setApplicationId(apln.getApplicationId());
			  et.setProcessId(apln.getProcessId());
			  Long newStartL= endDiff+startApp.getTime();
			  Timestamp newStart= new Timestamp(newStartL);
			  Long newEndL= endDiff+endDtTime.getTime();
			  Timestamp newEnd= new Timestamp(newEndL);
			  et.setNewEtaStartTransaction(newStart);
			  et.setNewEtaEndTransaction(newEnd);  
			  et.setProcessId(apln.getProcessId());
			  et.setEventDate(TreatmentDate.getInstance().getTreatmentDate());
			  if((et.getApplicationId()==applicationId) && (et.getProcessId()== processId))
				  et.setProblemFlag(1);
			  addNewEta(et);
	  }
		  System.out.println("new end time set and value returned");
		return et.getNewEtaEndTransaction();
  }*/
  
  public List<WtsNewEtaTab> getEtaByAppIdProcessId(int processId,int appId) {
	  try{
	  String hql="From WtsNewEtaTab where applicationId=? and processId=? and eventDate=?";
	List <WtsNewEtaTab> et= (List<WtsNewEtaTab>) entityManager.createQuery(hql).setParameter(1,appId).setParameter(2,processId).setParameter(3, TreatmentDate.getInstance().getTreatmentDate()).getSingleResult();
	  return et;
	  }catch (Exception e){
		  e.printStackTrace();
	  return null;
	  }
  }
  
  public WtsNewEtaTab EtaProcessTime(int processId){
	  WtsNewEtaTab eta= new WtsNewEtaTab();
	  WtsNewEtaTab etS=new WtsNewEtaTab();
	  WtsNewEtaTab etE=new WtsNewEtaTab();
	  int fApp= appDAO.getFirstAppId(processId);
	  int lApp= appDAO.getLastAppId(processId);
	  etS= (WtsNewEtaTab) getEtaByAppIdProcessId(processId,fApp);
	  etE= (WtsNewEtaTab) getEtaByAppIdProcessId(processId,lApp);
	  if(etS!=null){
		  eta.setNewEtaStartTransaction(etS.getNewEtaStartTransaction());
	  }
	  else{
		  WtsAppTab app=appDAO.getAppById(fApp);
		  eta.setNewEtaEndTransaction(app.getStartTime());
	  }
	  
	  if(etE!=null){
		  eta.setNewEtaEndTransaction(etS.getNewEtaEndTransaction());
	  }
	  else{
		  WtsAppTab app=appDAO.getAppById(lApp);
		  eta.setNewEtaEndTransaction(app.getEndTime());
	  }
	  
	  eta.setProcessId(processId);
	  eta.setApplicationId(0);
	  eta.setEventDate(TreatmentDate.getInstance().getTreatmentDate());
	  eta.setProblemFlag(1);
	  return eta;
	  }		
	
  
  public List<WtsNewEtaTab> getAllEta() {
		String hql = "FROM WtsNewEtaTab as et ORDER BY et.applicationId";
		return (List<WtsNewEtaTab>) entityManager.createQuery(hql).getResultList();
	}	
}
