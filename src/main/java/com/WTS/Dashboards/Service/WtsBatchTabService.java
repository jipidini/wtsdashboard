package com.WTS.Dashboards.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.WTS.Dashboards.Entity.WtsBatchTab;
import com.WTS.Dashboards.dao.WtsBatchTabDao;


@Service
public class WtsBatchTabService implements IWtsServiceInterface{
    @Autowired
    private WtsBatchTabDao batDao;
	
	public List<WtsBatchTab> getAllBatches() {
		return batDao.getAllBatches();
	}

	
	public synchronized boolean addBatch(WtsBatchTab batch) {
		if (batDao.batchExists(batch.getName())) {
	    	   return false;
	       } else {
	    	   batDao.addBatch(batch);;
	    	   return true;
	       }
		
	}

	
	public void updateBatch(WtsBatchTab batch) {
		batDao.updateBatch(batch);
		
	}

	
	public void deleteBatch(int batchId) {
		batDao.deleteBatch(batchId);
		
	}

	
	public WtsBatchTab getBatchById(int batchId) {
		WtsBatchTab obj =batDao.getBatchById(batchId);
		return obj;
	}

}
