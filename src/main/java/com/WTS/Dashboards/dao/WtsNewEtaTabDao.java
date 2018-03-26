package com.WTS.Dashboards.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.WTS.Dashboards.Entity.WtsAppTab;
import com.WTS.Dashboards.Entity.WtsNewEtaTab;
import com.WTS.Dashboards.Utility.FileCreationTime;
import com.WTS.Dashboards.Utility.TreatmentDate;


@Transactional
@Repository
public class WtsNewEtaTabDao implements IWtsDaoInterface {

	@PersistenceContext	
	private EntityManager entityManager;
	

	
	public WtsNewEtaTab getEtaById(int newEtaId) {
		return entityManager.find(WtsNewEtaTab.class, newEtaId);
	}
	
	public void addNewEta(WtsNewEtaTab newEta) {
		entityManager.persist(newEta);
		
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
  
  
  public Timestamp newEtaStartCalculation(WtsAppTab app ,Date newStartDTTime,int applicationId, int processId){
	  System.out.println("start time loop entered");
	  String name= app.getName();
	  int apId=applicationId;
	  WtsNewEtaTab et= new WtsNewEtaTab();
	  Timestamp startDTTime=app.getStartTime();
	 Timestamp fileStart= FileCreationTime.startTimestamp(name);
	  Long startDiff= (fileStart.getTime()-startDTTime.getTime());
//	  Timestamp start= new Timestamp(StartDiff);
	  String hql= "From WtsAppTab as apn where apn.applicationId>=?";
		List<WtsAppTab> ap= (List<WtsAppTab>) entityManager.createQuery(hql).setParameter(1,apId).getResultList();
		 Iterator<WtsAppTab> apIt=ap.iterator();
		  while(apIt.hasNext()){
			  WtsAppTab apln = (WtsAppTab) apIt.next();
			  Timestamp startApp= apln.getStartTime();
			  Timestamp endDtTime= app.getEndTime();
			  Long newStartL= startDiff+startApp.getTime();
			  Timestamp newStart= new Timestamp(newStartL);
			  Long newEndL= startDiff+endDtTime.getTime();
			  Timestamp newEnd= new Timestamp(newEndL);
			  et.setNewEtaEndTransaction(newEnd);  
			  et.setNewEtaStartTransaction(newStart); 
			  et.setApplicationId(applicationId);
			  et.setProcessId(processId);
			  et.setEventDate(TreatmentDate.getInstance().getTreatmentDate());
			  if((et.getApplicationId()==applicationId) && (et.getProcessId()== processId))
			  et.setProblemFlag(1);
			  //if(et.getEtaId()>0)
			  addNewEta(et);
			  
	  }
		  //System.out.println("new start time is "+fileStart);
		  System.out.println("new start time set and value returned"+et.getNewEtaStartTransaction());
		  return et.getNewEtaStartTransaction();
  }
  
  
  public Timestamp newEtaEndCalculation(WtsAppTab app,Date newEndDTTime,int applicationId, int processId){
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
  }
}
