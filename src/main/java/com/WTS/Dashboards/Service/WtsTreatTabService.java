package com.WTS.Dashboards.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.WTS.Dashboards.Entity.WtsTreatTab;
import com.WTS.Dashboards.dao.WtsAppTabDao;
import com.WTS.Dashboards.dao.WtsTreatTabDao;

@Service
public class WtsTreatTabService implements IWtsServiceInterface {

	@Autowired
	private WtsTreatTabDao trD;
	
	@Autowired
	private WtsAppTabDao aDao;



	
	public void addDate() {
	if(trD.dateExists()){
		System.out.println("date present");
	}
	else{
		trD.addTreatment();
	}
	}
	
	
	public List<WtsTreatTab> getAllData(){
		System.out.println("all data loop entered");
		return trD.getAllData();
	}
}
