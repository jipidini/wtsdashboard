package com.WTS.Dashboards.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.WTS.Dashboards.Entity.WtsTransTab;
import com.WTS.Dashboards.Entity.WtsTrigTab;

@Transactional
@Repository
public class WtsTrigTabDao implements IWtsDaoInterface{
	@PersistenceContext	
	private EntityManager entityManager;
	
	
	public WtsTrigTab getTrigById(int trigId) {
		return entityManager.find(WtsTrigTab.class,trigId);
	}
	
	@SuppressWarnings("unchecked")
	
	public List<WtsTrigTab> getAlltrig() {
		String hql = "FROM WtsTrigTab as trig ORDER BY trig.trigId";
		return (List<WtsTrigTab>) entityManager.createQuery(hql).getResultList();
	}

	
	public void addTrig(WtsTrigTab trig) {
		entityManager.persist(trig);
		}

	
	public void updateTrig(WtsTrigTab trig) {
		WtsTrigTab tr = getTrigById(trig.getTrigId());
		tr.setLastUpdateTime(trig.getLastUpdateTime());
		tr.setTriggerFilePath(trig.getTriggerFilePath());
	}

	
	public void deleteTrig(int trigId) {
		entityManager.remove(getTrigById(trigId));
		
	}

	

	
	public boolean trigExists(int trigId) {
		String hql = "FROM WtsTrigTab as trig WHERE trans.trigId = ?";
		int count = entityManager.createQuery(hql).setParameter(1, trigId).getResultList().size();
		return count > 0 ? true : false;
	
	}

}
