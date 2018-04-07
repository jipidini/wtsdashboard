package com.WTS.Dashboards.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.WTS.Dashboards.Entity.WtsAppMappingTab;
import com.WTS.Dashboards.Entity.WtsAppTab;
import com.WTS.Dashboards.Entity.WtsProcessAppMapTab;


@Transactional
@Repository
	public class WtsAppMappingTabDao implements IWtsDaoInterface {
	
	@PersistenceContext	
	private EntityManager entityManager;
	
	
	public WtsAppMappingTab getAppMappingById(int batchId) {
		return entityManager.find(WtsAppMappingTab.class, batchId);
		
	}
	
	@SuppressWarnings("unchecked")
	
	public List<WtsAppMappingTab> getAllAppMappings() {
		String hql = "FROM WtsAppMappingTab as mapping ORDER BY mapping.appMappingId";
		return (List<WtsAppMappingTab>) entityManager.createQuery(hql).getResultList();
		
	}

	public WtsAppMappingTab getAppMappingsByParent(int parentId,int applicationId, int processId) {
		 System.out.println("parentId"+parentId);
		   String hql="FROM WtsAppMappingTab as app WHERE app.parentId=:parent AND app.childId=:child AND app.processId=:PROC";
		   
		 List <WtsAppMappingTab> ls=entityManager.createQuery(hql).setParameter("parent",parentId).setParameter("child", applicationId).setParameter("PROC", processId).getResultList();
		  if(ls!=null && !ls.isEmpty())
			  return ls.get(0);
		  else
	   return null;
	}
	
	
	public List<WtsAppMappingTab> getAllAppMappingsByParent(int parentId, int processId) {
		 System.out.println("parentId"+parentId);
		   String hql="FROM WtsAppMappingTab as app WHERE app.parentId=:parent and app.processId=:processId";
		   
		 List <WtsAppMappingTab> ls=entityManager.createQuery(hql).setParameter("parent",parentId).setParameter("processId",processId).getResultList();
		  if(ls!=null && !ls.isEmpty())
			  return ls;
		  else
	   return null;
	}
	
	
	public void addAppMappings(WtsAppMappingTab mapping) {
		entityManager.persist(mapping);
		
	}

	
	public void updateAppMapping(WtsAppMappingTab mapping) {
		WtsAppMappingTab map = getAppMappingById(mapping.getAppMappingId());
		map.setComments(mapping.getComments());
		map.setWeight(mapping.getWeight());
		entityManager.flush();
		
	}

	
	public void deleteAppMapping(int appMappingId) {
		entityManager.remove(getAppMappingById(appMappingId));
		
	}
	
	
	
	
	public boolean appMappingExists(String name) {
		String hql = "FROM WtsAppMappingTab as bat WHERE bat.name = ?";
		int count = entityManager.createQuery(hql).setParameter(1, name).getResultList().size();
		return count > 0 ? true : false;
	}
}
