package com.WTS.Dashboards.Utility;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.WTS.Dashboards.dao.WtsNewEtaTabDao;

public class DateUtility {
	@Autowired
	private static WtsNewEtaTabDao etD;
	
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
	
	public static boolean isAfterOrSame(Timestamp expTime, Date actuleTime) {
		String starttime = new SimpleDateFormat("HH:mm:ss").format(expTime);
		 
		  String startTimeParse[] = starttime.split(":");

		  int firstHour = Integer.parseInt(startTimeParse[0]);
		  int firstMinute = Integer.parseInt(startTimeParse[1]);
		  int firstSec = Integer.parseInt(startTimeParse[2]);
		  
		  Calendar calexp=Calendar.getInstance();
		  calexp.set(Calendar.HOUR_OF_DAY, firstHour);
		  calexp.set(Calendar.MINUTE, firstMinute);
		  calexp.set(Calendar.SECOND, firstSec);
		
		  Calendar calActual=Calendar.getInstance();
		  
		  calActual.setTime(actuleTime);
		  if(calexp.getTimeInMillis() >= calActual.getTimeInMillis()) {
			return true;
		  }else {
			  return false;
		  }
		 
	}
	
	public static Date getNewStartETA(Date oldStartDTTime, Date newStartDTTime, Date oldEndDtTime) {
		Date newStartETA=null;
		return newStartETA;
	}
	
	public static Date getNewEndETA(Date oldStartDTTime, Date newStartDTTime, Date oldEndDtTime) {
		Date newEndETA=null;
		//Get diff and sum or with oldEndtime and return that as newEndETA
		return newEndETA;
	}
	
	public static Timestamp addBufferTime(Timestamp startDTTime, int bufferTime) throws ParseException {
		// TODO Auto-generated method stub
		String starttime = new SimpleDateFormat("HH:mm:ss").format(startDTTime);
		 
		  String startTimeParse[] = starttime.split(":");

		  int firstHour = Integer.parseInt(startTimeParse[0]);
		  int firstMinute = Integer.parseInt(startTimeParse[1]);
		  int firstSec = Integer.parseInt(startTimeParse[2]);
		  
		  Calendar calstart=Calendar.getInstance();
		  calstart.set(Calendar.HOUR_OF_DAY, firstHour);
		  calstart.set(Calendar.MINUTE, firstMinute);
		  calstart.set(Calendar.SECOND, firstSec);
		  
		  System.out.println("FirstMinute"+firstMinute);
		  //Calendar.minutes
		  calstart.add(Calendar.MINUTE,bufferTime);
		 
		  System.out.println("Calstarttime"+calstart.getTime().getTime());
		  
				  return new Timestamp(calstart.getTime().getTime());
	}
}
