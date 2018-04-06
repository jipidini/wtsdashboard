package com.WTS.Dashboards.dao;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.WTS.Dashboards.Entity.WtsProcessAppMapTab;






@Transactional
@Repository
	public class WtsProcessAppMapTabDao implements IWtsDaoInterface {
	
	@PersistenceContext	
	private EntityManager entityManager;
	
	
	public WtsProcessAppMapTab getProcessAppMappingById(int batchId) {
		return entityManager.find(WtsProcessAppMapTab.class, batchId);
		
	}
	
	public WtsProcessAppMapTab getAllAppMappingsByProcess(int processId,int applicationId) {
		 System.out.println("processId"+processId);
		   String hql="FROM WtsProcessAppMapTab as app WHERE app.processId=:proc AND app.applicationId=:app";
		   
		 List <WtsProcessAppMapTab> ls=entityManager.createQuery(hql).setParameter("proc",processId).setParameter("app", applicationId).getResultList();
		  if(ls!=null && !ls.isEmpty())
			  return ls.get(0);
		  else
	   return null;
	}
	
	public Timestamp getAppMappingStartTime(int processId,int applicationId) {
		   String hql="FROM WtsProcessAppMapTab as app WHERE app.processId=:proc AND app.applicationId=:app";
		   
		 List <WtsProcessAppMapTab> ls=entityManager.createQuery(hql).setParameter("proc",processId).setParameter("app", applicationId).getResultList();
		  if(ls!=null && !ls.isEmpty() && ls.get(0)!=null)
			  return ls.get(0).getStartTime();
		  else
	   return null;
	}
	
	public Timestamp getAppMappingEndTime(int processId,int applicationId) {
		   String hql="FROM WtsProcessAppMapTab as app WHERE app.processId=:proc AND app.applicationId=:app";
		   
		 List <WtsProcessAppMapTab> ls=entityManager.createQuery(hql).setParameter("proc",processId).setParameter("app", applicationId).getResultList();
		  if(ls!=null && !ls.isEmpty() && ls.get(0)!=null)
			  return ls.get(0).getEndTime();
		  else
	   return null;
	}
	
	public int getAppMappingSequence(int processId,int applicationId) {
		   String hql="FROM WtsProcessAppMapTab as app WHERE app.processId=:proc AND app.applicationId=:app";
		   
		 List <WtsProcessAppMapTab> ls=entityManager.createQuery(hql).setParameter("proc",processId).setParameter("app", applicationId).getResultList();
		  if(ls!=null && !ls.isEmpty() && ls.get(0)!=null)
			  return ls.get(0).getSequence();
		  else
	   return 0;
	}
	
	public int getAppMappingBufferTime(int processId,int applicationId) {
		   String hql="FROM WtsProcessAppMapTab as app WHERE app.processId=:proc AND app.applicationId=:app";
		   
		 List <WtsProcessAppMapTab> ls=entityManager.createQuery(hql).setParameter("proc",processId).setParameter("app", applicationId).getResultList();
		  if(ls!=null && !ls.isEmpty() && ls.get(0)!=null)
			  return ls.get(0).getBufferTime();
		  else
	   return 0;
	}
	
	public String getAppMappingEmailId(int processId,int applicationId) {
		   String hql="FROM WtsProcessAppMapTab as app WHERE app.processId=:proc AND app.applicationId=:app";
		   
		 List <WtsProcessAppMapTab> ls=entityManager.createQuery(hql).setParameter("proc",processId).setParameter("app", applicationId).getResultList();
		  if(ls!=null && !ls.isEmpty() && ls.get(0)!=null)
			  return ls.get(0).getEmailId();
		  else
	   return null;
	}
	
	public String getAppMappingSupportContact(int processId,int applicationId) {
		   String hql="FROM WtsProcessAppMapTab as app WHERE app.processId=:proc AND app.applicationId=:app";
		   
		 List <WtsProcessAppMapTab> ls=entityManager.createQuery(hql).setParameter("proc",processId).setParameter("app", applicationId).getResultList();
		  if(ls!=null && !ls.isEmpty() && ls.get(0)!=null)
			  return ls.get(0).getSupportContact();
		  else
	   return null;
	}
	
	@SuppressWarnings("unchecked")
	
	public List<WtsProcessAppMapTab> getAllProcessAppMappings() {
		String hql = "FROM WtsProcessAppMapTab as mapping ORDER BY mapping.processAppMapId";
		return (List<WtsProcessAppMapTab>) entityManager.createQuery(hql).getResultList();
		
	}

	
	public void addProcessAppMappings(WtsProcessAppMapTab mapping) {
		entityManager.persist(mapping);
		
	}

	
	public void updateProcessAppMapping(WtsProcessAppMapTab mapping) {
		WtsProcessAppMapTab map = getProcessAppMappingById(mapping.getProcessAppMapId());
		map.setComments(mapping.getComments());
		map.setWeight(mapping.getWeight());
		entityManager.flush();
		
	}

	
	public void deleteProcessAppMapping(int appMappingId) {
		entityManager.remove(getProcessAppMappingById(appMappingId));
		
	}
	
	
	public boolean processAppMappingExists(String name) {
		String hql = "FROM WtsAppMappingTab as bat WHERE bat.name = ?";
		int count = entityManager.createQuery(hql).setParameter(1, name).getResultList().size();
		return count > 0 ? true : false;
	}
}
