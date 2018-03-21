package com.WTS.Dashboards.Utility;

public class SPD extends Thread {

	public void SPDObject() throws InterruptedException
	{
		 String path="cmd /c start C:\\Jar\\SPDstarttrig.bat";
		 String path1="cmd /c start C:\\Jar\\SPDendtrig.bat";
		 String path2="cmd /c start C:\\Jar\\SPDfailed.bat";
		 String path3="cmd /c start C:\\Jar\\clean.bat";
	      new ReminderBeep(5,path);
		new ReminderBeep(10,path1);
		Thread.sleep(2000);
		new ReminderBeep(15,path2);
	
	new ReminderBeep(20,path3);
	
	}
	
}
