package com.WTS.Dashboards.Service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.WTS.Dashboards.Entity.WtsAppTab;
import com.WTS.Dashboards.Entity.WtsNewEtaTab;
import com.WTS.Dashboards.dao.WtsNewEtaTabDao;

@Service
public class WtsNewEtaTabService implements IWtsServiceInterface{

	@Autowired
	private WtsNewEtaTabDao etDao;
	
	public List<WtsNewEtaTab> getAllEta() {
		return etDao.getAllEta();
		
	}
	
	/*public Timestamp newEtaStartCalculation(WtsAppTab app){
		return etDao.newEtaStartCalculation(app);
	}
	
	public Timestamp newEtaEndCalculation(WtsAppTab app){
		return etDao.newEtaEndCalculation(app);
	}*/
}
