package com.WTS.Dashboards;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;



@SpringBootApplication
@EnableCaching
public class DashboardsApplication {

	DashboardsApplication() throws Exception
	{
		
		 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    // Calendar yesterdayCal = Calendar.getInstance();
			Properties prop=new Properties();
		
			
//	FileCreationTime.getTriggerFileStatus();
	}
	
	public static void main(String[] args) {
		
	
		SpringApplication.run(DashboardsApplication.class, args);
	}
}
