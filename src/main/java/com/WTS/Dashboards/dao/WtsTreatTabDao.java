package com.WTS.Dashboards.dao;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.WTS.Dashboards.Entity.WtsAppTab;
import com.WTS.Dashboards.Entity.WtsTransTab;
import com.WTS.Dashboards.Entity.WtsTreatTab;
import com.WTS.Dashboards.Utility.FileCreationTime;
import com.WTS.Dashboards.dao.WtsAppTabDao;

@Transactional
@Repository

public class WtsTreatTabDao implements IWtsDaoInterface{
	@PersistenceContext	
	private EntityManager entityManager;
	
	@Autowired
	private WtsAppTabDao aDao;

	
public boolean dateExists(){
		
		String hql="FROM WtsTreatTab as tr where tr.treatmentDate= current_date";
		int count = entityManager.createQuery(hql).getResultList().size();
		if(count>0){
			return true;
		}
		else{
			return false;
		}
	}


public void addTreatment(Date dt){
	/*System.out.println("add treatment entered");
	WtsTreatTab	t=new WtsTreatTab();
	t.setTreatmentDate(dt);
	entityManager.persist(t);*/
}


public void addTreatment(){
	Date d=new Date();
	WtsTreatTab	t=new WtsTreatTab();
	t.setTreatmentDate(d);
	entityManager.persist(t);
}


public List<WtsTreatTab> getAllData() {
	String hql = "FROM WtsTreatTab as trt";
	return (List<WtsTreatTab>) entityManager.createQuery(hql).getResultList();
}


public boolean dateExists(Date d) {
	// TODO Auto-generated method stub
	return false;
}	


}
