package com.WTS.Dashboards.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.WTS.Dashboards.Entity.WtsProcessAppMapTab;
import com.WTS.Dashboards.dao.WtsProcessAppMapTabDao;





@Service
public class WtsProcessAppMapTabService implements IWtsServiceInterface{
    @Autowired
    private WtsProcessAppMapTabDao mappingDao;
	
	public List<WtsProcessAppMapTab> getAllAppMappings() {
		return mappingDao.getAllProcessAppMappings();
	}

	
	public synchronized boolean addProcessAppMapping(WtsProcessAppMapTab appMapping) {
		if (mappingDao.processAppMappingExists(appMapping.getName())) {
	    	   return false;
	       } else {
	    	   mappingDao.addProcessAppMappings(appMapping);
	    	   return true;
	       }
		
	}

	
	public void updateProcessAppMapping(WtsProcessAppMapTab appMapping) {
		mappingDao.updateProcessAppMapping(appMapping);
		
	}

	
	public void deleteProcessAppMapping(int batchId) {
		mappingDao.deleteProcessAppMapping(batchId);
		
	}

	
	public WtsProcessAppMapTab getProcessAppMappingById(int batchId) {
		WtsProcessAppMapTab obj =mappingDao.getProcessAppMappingById(batchId);
		return obj;
	}

}
