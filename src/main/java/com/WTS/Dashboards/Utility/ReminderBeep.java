package com.WTS.Dashboards.Utility;
import static java.lang.Thread.currentThread;

import java.util.Timer;
import java.util.TimerTask;
import java.awt.Toolkit;
import java.io.IOException;

public class ReminderBeep {
    Toolkit toolkit;
    Timer timer;

    public ReminderBeep(int seconds,String path) {
	toolkit = Toolkit.getDefaultToolkit();
        timer = new Timer();        
        timer.schedule(new RemindTask(path), seconds*1000);
    }

    class RemindTask extends TimerTask {
    	String path;
        public RemindTask(String path) {
			// TODO Auto-generated constructor stub
        	this.path=path;
		}

		public void run() {
            System.out.println("Time's up!");
           
			Runtime rn=Runtime.getRuntime();
			try {
				Process pr=rn.exec(path);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	    toolkit.beep();
	    timer.cancel(); //Not necessary because we call System.exit
//	    System.exit(0);   //Stops the AWT thread (and everything else)
        }
    }

   /* 
    public static void main(String args[]) {
	System.out.println("About to schedule task.");
	 String path="cmd /c start C:\\Jar\\starttrig.bat";
	 String path1="cmd /c start C:\\Jar\\endtrig.bat";
	 String path2="cmd /c start C:\\Jar\\failed.bat";
        new ReminderBeep(5,path);
	System.out.println("Task scheduled.");
	new ReminderBeep(10,path1);
	new ReminderBeep(15,path2);
    }
*/
	  } 