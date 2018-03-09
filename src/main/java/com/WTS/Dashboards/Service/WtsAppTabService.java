package com.WTS.Dashboards.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.WTS.Dashboards.Entity.WtsAppTab;
import com.WTS.Dashboards.dao.WtsAppTabDao;
@Service
public class WtsAppTabService implements IWtsServiceInterface{
	@Autowired
	private WtsAppTabDao appDao;
	
	public List<WtsAppTab> getAllApps() {
		return appDao.getAllApps();
		
	}

	
	public  synchronized boolean addApp(WtsAppTab application) {
		if (appDao.appExists(application.getProcessId(),application.getApplicationId(),application.getLastUpdateTime())) {
	    	   return false;
	       } else {
	    	   appDao.addApp(application);;
	    	   return true;
	       }
		
	}

	
	public void updateApp(WtsAppTab application) {
		appDao.updateApp(application);
		
	}

	
	public void deleteApp(int applicationId) {
		appDao.deleteApp(applicationId);
		
	}

	
	public WtsAppTab getAppById(int applicationId) {
		WtsAppTab obj = appDao.getAppById(applicationId);
		return obj;
	}
}
