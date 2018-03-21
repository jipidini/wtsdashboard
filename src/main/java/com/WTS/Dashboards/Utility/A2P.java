package com.WTS.Dashboards.Utility;

public class A2P extends Thread {

	public void A2PObject() throws InterruptedException
	{
		 String path="cmd /c start C:\\Jar\\A2Pstarttrig.bat";
		 String path1="cmd /c start C:\\Jar\\A2Pendtrig.bat";
		 String path2="cmd /c start C:\\Jar\\A2Pfailed.bat";
		 String path3="cmd /c start C:\\Jar\\clean.bat";
	    new ReminderBeep(6,path);
		new ReminderBeep(12,path1);
		Thread.sleep(3000);
		new ReminderBeep(18,path2);
//		new ReminderBeep(20,path3);
	
	}
}
