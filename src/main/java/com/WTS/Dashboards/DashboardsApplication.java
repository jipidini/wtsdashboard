package com.WTS.Dashboards;

import static java.lang.Thread.currentThread;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.WTS.Dashboards.Utility.A2P;
//import com.WTS.Dashboards.Utility.MyThread;
import com.WTS.Dashboards.Utility.PMB;
import com.WTS.Dashboards.Utility.SPD;




@SpringBootApplication
@EnableCaching
@EnableScheduling
@ComponentScan({ "com.WTS.Dashboards.Utility" })
public class DashboardsApplication {


	static {
		SPD sp=new SPD();
		try {
			sp.SPDObject();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	static void runtrigA2P() throws InterruptedException
	{
		A2P a2p=new A2P();
		a2p.A2PObject();
		
	}
	void runPMB() throws InterruptedException
	{
		PMB pmb=new PMB();
		pmb.PMBObject();
	}
	public static void main(String[] args) throws InterruptedException {
		
		SpringApplication.run(DashboardsApplication.class, args);
		DashboardsApplication.runtrigA2P();
		DashboardsApplication db=new DashboardsApplication();
		db.runPMB();
	}
}
