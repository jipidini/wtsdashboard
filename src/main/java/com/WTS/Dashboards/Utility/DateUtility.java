package com.WTS.Dashboards.Utility;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtility {

	
	public static boolean isAfterNow(Timestamp startDTTime) {
		String starttime = new SimpleDateFormat("HH:mm:ss").format(startDTTime);
		 
		  String startTimeParse[] = starttime.split(":");

		  int firstHour = Integer.parseInt(startTimeParse[0]);
		  int firstMinute = Integer.parseInt(startTimeParse[1]);
		  int firstSec = Integer.parseInt(startTimeParse[2]);
		  
		  Calendar calstart=Calendar.getInstance();
		  calstart.set(Calendar.HOUR_OF_DAY, firstHour);
		  calstart.set(Calendar.MINUTE, firstMinute);
		  calstart.set(Calendar.SECOND, firstSec);
		
		  Calendar calNow=Calendar.getInstance();
		  if(calNow.getTimeInMillis()<calstart.getTimeInMillis()) {
			return false;
		  }else {
			  return true;
		  }
		 
	}
	
	public static Date getNewEndETA(Date oldStartDTTime, Date newStartDTTime, Date oldEndDtTime) {
		Date newEndETA=null;
		//Get diff and sum or with oldEndtime and return that as newEndETA
		return newEndETA;
	}
}
