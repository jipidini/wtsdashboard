package com.WTS.Dashboards.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.WTS.Dashboards.Entity.WtsAppMappingTab;
import com.WTS.Dashboards.dao.WtsAppMappingTabDao;




@Service
public class WtsAppMappingTabService implements IWtsServiceInterface{
    @Autowired
    private WtsAppMappingTabDao mappingDao;
	
	public List<WtsAppMappingTab> getAllAppMappings() {
		return mappingDao.getAllAppMappings();
	}

	
	public synchronized boolean addAppMapping(WtsAppMappingTab appMapping) {
		if (mappingDao.appMappingExists(appMapping.getName())) {
	    	   return false;
	       } else {
	    	   mappingDao.addAppMappings(appMapping);
	    	   return true;
	       }
		
	}

	
	public void updateAppMapping(WtsAppMappingTab appMapping) {
		mappingDao.updateAppMapping(appMapping);
		
	}

	
	public void deleteAppMapping(int batchId) {
		mappingDao.deleteAppMapping(batchId);
		
	}

	
	public WtsAppMappingTab getAppMappingById(int batchId) {
		WtsAppMappingTab obj =mappingDao.getAppMappingById(batchId);
		return obj;
	}

}
