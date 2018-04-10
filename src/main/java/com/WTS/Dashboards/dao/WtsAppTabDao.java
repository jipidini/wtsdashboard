
package com.WTS.Dashboards.dao;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.WTS.Dashboards.DTO.Application;
import com.WTS.Dashboards.Entity.WtsAppMappingTab;
import com.WTS.Dashboards.Entity.WtsAppTab;
import com.WTS.Dashboards.Entity.WtsNewEtaTab;
import com.WTS.Dashboards.Entity.WtsProcessAppMapTab;
import com.WTS.Dashboards.Entity.WtsTransTab;
import com.WTS.Dashboards.Utility.TreatmentDate;

@Transactional
@Repository
public class WtsAppTabDao implements IWtsDaoInterface{
	@PersistenceContext	
	private EntityManager entityManager;
	@Autowired
	private WtsProcessAppMapTabDao appProcMapDao;
	@Autowired
	private WtsAppMappingTabDao appMapDao;
	
	
	
	public WtsAppTab getAppById(int applicationId) {
		return entityManager.find(WtsAppTab.class, applicationId);
	}
	@SuppressWarnings("unchecked")
	
	public List<WtsAppTab> getAllApps() {
		String hql = "FROM WtsAppTab as app ORDER BY app.applicationId";
		return (List<WtsAppTab>) entityManager.createQuery(hql).getResultList();
	}	

	public List<WtsAppTab> getAllAppsByProcess(int processId) {
		List<WtsAppTab> result=null;
			   String hql="FROM WtsProcessAppMapTab as app WHERE app.processId=:proc";
			   
			 List <WtsProcessAppMapTab> ls=entityManager.createQuery(hql).setParameter("proc",processId).getResultList();
			 if(ls!=null && !ls.isEmpty()) {
				 result= new ArrayList<>();
				 Iterator<WtsProcessAppMapTab> mappItr=ls.iterator();
				 while (mappItr.hasNext()) {
					WtsProcessAppMapTab wtsProcessAppMapTab = (WtsProcessAppMapTab) mappItr.next();
					result.add(this.getAppById(wtsProcessAppMapTab.getApplicationId()));
				}
			 }
		  
	   return result;
	}
	
	public void addApp(WtsAppTab application) {
		entityManager.persist(application);
	}
	 public Application convertApplicationDTOforProcess(WtsAppTab application, int processId) {
		 Application appDTO=null;
		 if(application!=null) {
			 appDTO= new Application();
			 appDTO.setApplicationId(application.getApplicationId());
			 appDTO.setName(application.getName());
			 if(processId>0)
			 appDTO.setProcessId(processId);
			 appDTO.setComments(application.getComments());
			 WtsProcessAppMapTab appMap=appProcMapDao.getAllAppMappingsByProcess(processId,application.getApplicationId());
			 if(appMap!=null) {
				 appDTO.setSequence(appMap.getSequence());
				 appDTO.setTrigId(appMap.getTrigId());
				 appDTO.setTrigId(appMap.getTrigId());
				 appDTO.setWeight(appMap.getWeight());
				 appDTO.setBufferTime(appMap.getBufferTime());
				 appDTO.setEmailId(appMap.getEmailId());
				 appDTO.setSupportContact(appMap.getSupportContact());
				 appDTO.setStartTime(appMap.getStartTime());
				 appDTO.setEndTime(appMap.getEndTime());
			 }
			 appDTO.setLastUpdateTime(application.getLastUpdateTime());
			 appDTO.setEnableFlag(application.getEnableFlag());
			 appDTO.setEta(this.getETAProcessTxn(processId,TreatmentDate.getInstance().getTreatmentDate()));
			 appDTO.setTran(this.getTdyProcessTxn(processId,TreatmentDate.getInstance().getTreatmentDate()));
		 }
		 return appDTO;
	 }
	
	 public Application convertApplicationDTO(int parentId, WtsAppTab child,int processId) {
		 Application appDTO=null;
		 if(child!=null) {
			 appDTO= new Application();
			 appDTO.setApplicationId(child.getApplicationId());
			 appDTO.setName(child.getName());
			 appDTO.setComments(child.getComments());
			 WtsAppMappingTab appMap=appMapDao.getAppMappingsByParent(parentId,child.getApplicationId(),processId);
			 if(appMap!=null) {
				 appDTO.setSequence(appMap.getSequence());
				 appDTO.setTrigId(appMap.getTrigId());
				 appDTO.setWeight(appMap.getWeight());
				 appDTO.setBufferTime(appMap.getBufferTime());
				 appDTO.setEmailId(appMap.getEmailId());
				 appDTO.setSupportContact(appMap.getSupportContact());
				 appDTO.setStartTime(appMap.getStartTime());
				 appDTO.setEndTime(appMap.getEndTime());
			 }
			 appDTO.setLastUpdateTime(child.getLastUpdateTime());
			 appDTO.setEnableFlag(child.getEnableFlag());
			 appDTO.setEta(child.getEta());
		 }
		 return appDTO;
	 }
	 
	public void updateApp(WtsAppTab application) {
		WtsAppTab app = getAppById(application.getApplicationId());
		app.setComments(application.getComments());
		entityManager.flush();
	}
	
	
	public void deleteApp(int applicationId) {
		entityManager.remove(getAppById(applicationId));
	}
//	
//	public boolean appExists(String name) {
//		String hql = "FROM WtsAppTab as app WHERE app.name = ?";
//		int count = entityManager.createQuery(hql).setParameter(1, name).getResultList().size();
//		return count > 0 ? true : false;
//	}
//		
	
	public boolean appExists(int applicationId, Timestamp time ){
	String hql= "FROM WtsAppTab as app WHERE app.application_id = ? and app.last_updated_time=?";
	int cnt = entityManager.createQuery(hql).setParameter(1, applicationId).setParameter(1, time).getResultList().size();
	return cnt > 0 ? true : false;
	}
	
	/*public WtsAppTab getApp(int processId, int applicationId){
		if(findApp(processId, applicationId)){
			String hq= "FROM WtsAppTab as app WHERE app.process_id=? and app.application_id = ? ";
			WtsAppTab ap= (WtsAppTab) entityManager.createQuery(hq).setParameter(1,processId ).setParameter(2,applicationId).getResultList();
			return ap;
			else{
				return addApp(
			}
		}*/
	
	public void saveApp(){
		entityManager.flush();
	}
	
	public void updateData(int processId, int applicationId, int trigId) {
		// TODO Auto-generated method stub
		 
		
	}
	
	public int getFirstAppId(int processId){
		String hql= "select applicationId from WtsAppTab where processId=? and sequence=1 ";
		int id = (int) entityManager.createQuery(hql).setParameter(1,processId).getSingleResult();
		return id;
	}
	
	public int getLastAppId(int processId){
		String hqls= "select max(sequence) from WtsAppTab where processId=?";
		int seq = (int) entityManager.createQuery(hqls).setParameter(1,processId).getSingleResult();
		String hql= "select applicationId from WtsAppTab where processId=? and sequence=?";
		int id = (int) entityManager.createQuery(hql).setParameter(1,processId).setParameter(2,seq).getSingleResult();
		return id;
	}
	
	public int getLastSeq(int processId){
		String hqls= "select max(sequence) from WtsAppTab where processId=?";
		int seq = (int) entityManager.createQuery(hqls).setParameter(1,processId).getSingleResult();
		return seq;
	}
	
	public Set<WtsTransTab> getTdyProcessTxn(int processId, String trtDt) {
		String hql = "from WtsTransTab WHERE processId=? and eventDate= ? AND parent_id is NULL and application_id IS NOT null and childId IS NULL";

		Query qry = entityManager.createQuery(hql);
		qry.setParameter(1, processId);
		qry.setParameter(2, trtDt);
		if (qry.getResultList() != null && !qry.getResultList().isEmpty())
			return (Set<WtsTransTab>) qry.getResultList().get(0);
		else
			return null;

	}
	
	public Set<WtsNewEtaTab> getETAProcessTxn(int processId, String trtDt) {
		String hql = "from WtsNewEtaTab WHERE processId=? and eventDate= ? AND parent_id is NULL and application_id IS NOT null and childId IS NULL";

		Query qry = entityManager.createQuery(hql);
		qry.setParameter(1, processId);
		qry.setParameter(2, trtDt);
		if (qry.getResultList() != null && !qry.getResultList().isEmpty())
			return (Set<WtsNewEtaTab>) qry.getResultList().get(0);
		else
			return null;

	}
	
	public Set<WtsTransTab> getTdyBatchTxn(int processId, String trtDt, int childId) {
		String hql = "from WtsTransTab WHERE processId=? and eventDate= ? AND child_id= ? and application_id IS null ";

		Query qry = entityManager.createQuery(hql);
		qry.setParameter(1, processId);
		qry.setParameter(2, trtDt);
		qry.setParameter(3, childId);
		if (qry.getResultList() != null && !qry.getResultList().isEmpty())
			return (Set<WtsTransTab>) qry.getResultList().get(0);
		else
			return null;

	}
	
	public Set<WtsNewEtaTab> getETABatchTxn(int processId, String trtDt,int parentId) {
		String hql = "from WtsNewEtaTab WHERE processId=? and eventDate= ? AND parent_id= ? and application_id IS null and childId IS NOT NULL";

		Query qry = entityManager.createQuery(hql);
		qry.setParameter(1, processId);
		qry.setParameter(2, trtDt);
		qry.setParameter(3, parentId);
		if (qry.getResultList() != null && !qry.getResultList().isEmpty())
			return (Set<WtsNewEtaTab>) qry.getResultList().get(0);
		else
			return null;

	}
	
	
}
