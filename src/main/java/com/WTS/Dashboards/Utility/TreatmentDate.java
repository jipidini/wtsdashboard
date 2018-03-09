package com.WTS.Dashboards.Utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TreatmentDate {
	private static TreatmentDate instance;
	
	private TreatmentDate() {
		
	}

	public static TreatmentDate getInstance() {
		if (instance==null) {
			instance= new TreatmentDate();
		}
		return instance;
	}
	
	public String getTreatmentDate() {
		Date treatmentDate= new Date();
		String treatmentDatetxt= new SimpleDateFormat("yyyy-MM-dd").format(treatmentDate);
		return treatmentDatetxt;
	}
	
	

}
