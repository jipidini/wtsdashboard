package com.WTS.Dashboards.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.WTS.Dashboards.Entity.WtsTrigTab;
import com.WTS.Dashboards.dao.WtsTrigTabDao;

@Service
public class WtsTrigTabService implements IWtsServiceInterface {

	@Autowired
	private WtsTrigTabDao trigDao;
	
	
	public List<WtsTrigTab> getAlltrig() {
		return trigDao.getAlltrig();
	}

	
	public synchronized boolean addTrigger(WtsTrigTab trig) {
		if(trigDao.trigExists(trig.getTrigId())){
			return false;
		}
			else
			{
				trigDao.addTrig(trig);
				return true;
			}
	}

	
	public void updateTrigger(WtsTrigTab trig) {
		trigDao.updateTrig(trig);
		
	}

	
	public void deleteTrigger(int trigId) {
		trigDao.deleteTrig(trigId);
		
	}

	
	public WtsTrigTab getTriggerById(int trigId) {
		WtsTrigTab tri= trigDao.getTrigById(trigId);
		return tri;
	}

}
