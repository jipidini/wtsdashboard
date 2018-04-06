package com.WTS.Dashboards.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.WTS.Dashboards.DTO.ApplicationMappingDTO;
import com.WTS.Dashboards.DTO.ProcessDTO;
import com.WTS.Dashboards.Entity.WtsProcessTab;
import com.WTS.Dashboards.Entity.WtsTransTab;
import com.WTS.Dashboards.dao.WtsProcessTabDao;
@Service
public class WtsProcessTabService implements IWtsServiceInterface {
	@Autowired
	private WtsProcessTabDao prosDao;
	
	
	public List<WtsProcessTab> getAllprocess() {
	 return prosDao.getAllprocess();
	}

		public List<ProcessDTO> getAllprocessDTOs() {
		 return prosDao.getAllprocessDTOs();
		}

		public List<ApplicationMappingDTO> getAllDTOsForApplication(int applicationId, int processId, int parentId) {
			 return prosDao.getAllDTOsForApplication(applicationId,processId,parentId);
			}
	
	public synchronized boolean addProcess(WtsProcessTab process) {
		if (prosDao.processExists(process.getName())) {
	    	   return false;
	       } else {
	    	   prosDao.addProcess(process);;
	    	   return true;
	       }
	}

	
	public WtsProcessTab getProcessById(int processId) {
		WtsProcessTab obj = prosDao.getProcessById(processId);
		return obj;
	}

	
	public void updateProcess(WtsProcessTab process) {
		prosDao.updateProcess(process);
		
	}

	
	public void deleteProcess(int processId) {
		prosDao.deleteProcess(processId);
		
	}

	
	public void updateTransactionModifiedDetail(WtsTransTab trans) {
		// TODO Auto-generated method stub
		
	}

	
	public WtsProcessTab getTransactionById(int processId) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
