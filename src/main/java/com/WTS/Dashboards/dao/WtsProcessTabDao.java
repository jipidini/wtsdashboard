package com.WTS.Dashboards.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.WTS.Dashboards.Entity.WtsProcessTab;

@Transactional
@Repository
public class WtsProcessTabDao implements IWtsDaoInterface {
	
	@PersistenceContext	
	private EntityManager entityManager;

	
	public WtsProcessTab getProcessById(int processId) {
		return entityManager.find(WtsProcessTab.class, processId);
	}
	@SuppressWarnings("unchecked")
	
	public List<WtsProcessTab> getAllprocess() {
		String hql = "FROM WtsProcessTab as pros ORDER BY pros.processId";
		return (List<WtsProcessTab>) entityManager.createQuery(hql).getResultList();
	}

	
	public void addProcess(WtsProcessTab process) {
		entityManager.persist(process);
		
	}

	
	public void updateProcess(WtsProcessTab process) {
		WtsProcessTab pros = getProcessById(process.getProcessId());
		pros.setComments(process.getComments());
		pros.setWeight(process.getWeight());
		entityManager.flush();
		
		
	}

	
	public void deleteProcess(int processId) {
		entityManager.remove(getProcessById(processId));
		
	}


	
	public boolean processExists(String name) {
		String hql = "Select pros.* FROM WtsProcessTab as pros WHERE pros.name = ?";
		int count = entityManager.createQuery(hql).setParameter(1, name).getResultList().size();
		return count > 0 ? true : false;
	}

	
	
}
