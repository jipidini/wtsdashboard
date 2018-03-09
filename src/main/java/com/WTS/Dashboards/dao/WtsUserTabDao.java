package com.WTS.Dashboards.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.WTS.Dashboards.Entity.WtsUserTab;

@Transactional
@Repository
public class WtsUserTabDao implements IWtsDaoInterface {

	@PersistenceContext	
	private EntityManager entityManager;
	
	
	public WtsUserTab getUserById(int userId) {
		return entityManager.find(WtsUserTab.class,userId);
	}
	
	@SuppressWarnings("unchecked")
	
	public List<WtsUserTab> getAllUser() {
		String hql = "FROM WtsUserTab as usr ORDER BY usr.user";
		return (List<WtsUserTab>) entityManager.createQuery(hql).getResultList();
	}

	
	public void addUser(WtsUserTab user) {
		entityManager.persist(user);
		
	}

	
	public void updateUser(WtsUserTab user) {
		WtsUserTab usr = getUserById(user.getUserId());
		usr.setComments(user.getComments());
		usr.setAppliactionId(user.getAppliactionId());
		entityManager.flush();
		
	}

	
	public void deleteUser(int userId) {
		entityManager.remove(getUserById(userId));
		
	}

	

	
	public boolean UserExists(int userId) {
		String hql = "FROM WtsUserTab as usr WHERE usr.userId = ?";
		int count = entityManager.createQuery(hql).setParameter(1, userId).getResultList().size();
		return count > 0 ? true : false;
	}

}
