package com.WTS.Dashboards.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.WTS.Dashboards.Entity.WtsBatchTab;

@Transactional
@Repository
	public class WtsBatchTabDao implements IWtsDaoInterface {
	
	@PersistenceContext	
	private EntityManager entityManager;
	
	
	public WtsBatchTab getBatchById(int batchId) {
		return entityManager.find(WtsBatchTab.class, batchId);
		
	}
	
	@SuppressWarnings("unchecked")
	
	public List<WtsBatchTab> getAllBatches() {
		String hql = "FROM WtsBatchTab as bat ORDER BY bat.batchId";
		return (List<WtsBatchTab>) entityManager.createQuery(hql).getResultList();
		
	}

	
	public void addBatch(WtsBatchTab batch) {
		entityManager.persist(batch);
		
	}

	
	public void updateBatch(WtsBatchTab batch) {
		WtsBatchTab bat = getBatchById(batch.getBatchId());
		batch.setComments(batch.getComments());
		batch.setWeight(batch.getWeight());
		entityManager.flush();
		
	}

	
	public void deleteBatch(int batchId) {
		entityManager.remove(getBatchById(batchId));
		
	}
	
	
	public boolean batchExists(String name) {
		String hql = "FROM WtsBatchTab as bat WHERE bat.name = ?";
		int count = entityManager.createQuery(hql).setParameter(1, name).getResultList().size();
		return count > 0 ? true : false;
	}
}
