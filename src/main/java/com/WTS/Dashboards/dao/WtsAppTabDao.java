
package com.WTS.Dashboards.dao;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.WTS.Dashboards.Entity.WtsAppTab;
import com.WTS.Dashboards.Entity.WtsTransTab;

@Transactional
@Repository
public class WtsAppTabDao implements IWtsDaoInterface{
	@PersistenceContext	
	private EntityManager entityManager;
	
	
	public WtsAppTab getAppById(int applicationId) {
		return entityManager.find(WtsAppTab.class, applicationId);
	}
	@SuppressWarnings("unchecked")
	
	public List<WtsAppTab> getAllApps() {
		String hql = "FROM WtsAppTab as app ORDER BY app.applicationId";
		return (List<WtsAppTab>) entityManager.createQuery(hql).getResultList();
	}	

	public List<WtsAppTab> getAllAppsByProcess(int processId) {
		 System.out.println("processId"+processId);
		   String hql="FROM WtsAppTab as app WHERE app.processId=:proc";
		   
		 List <WtsAppTab> ls=entityManager.createQuery(hql).setParameter("proc",processId).getResultList();
		  
	   return ls;
	}
	
	public void addApp(WtsAppTab application) {
		entityManager.persist(application);
	}
	
	
	public void updateApp(WtsAppTab application) {
		WtsAppTab app = getAppById(application.getApplicationId());
		app.setComments(application.getComments());
		app.setWeight(application.getWeight());
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
	
	public boolean appExists(int processId, int applicationId, Timestamp time ){
	String hql= "FROM WtsAppTab as app WHERE app.process_id=? and app.application_id = ? and app.last_updated_time=?";
	int cnt = entityManager.createQuery(hql).setParameter(1,processId ).setParameter(2, applicationId).setParameter(3, time).getResultList().size();
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

}
