package com.WTS.Dashboards.Utility;

public class PMB {
	public void PMBObject() throws InterruptedException
	{
		 String path="cmd /c start C:\\Jar\\PMBstarttrig.bat";
		 String path1="cmd /c start C:\\Jar\\PMBendtrig.bat";
		 String path2="cmd /c start C:\\Jar\\PMBfailed.bat";
		 String path3="cmd /c start C:\\Jar\\clean.bat";
	    new ReminderBeep(6,path);
		new ReminderBeep(10,path1);
		Thread.sleep(200);
		new ReminderBeep(16,path2);
		new ReminderBeep(18,path3);
		System.gc();
	}
}
