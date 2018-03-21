package com.WTS.Dashboards.Utility;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.*;
import java.util.Calendar;
import java.util.Date;

import org.springframework.web.servlet.mvc.LastModified;

import com.WTS.Dashboards.Entity.WtsTransTab;
import com.WTS.Dashboards.Entity.WtsTrigTab;
import com.fasterxml.jackson.annotation.JsonFormat;

import ch.qos.logback.classic.net.SyslogAppender;

public class FileCreationTime {
	static File filePath2=new File("C:/Test/myBatch_end.txt");
public static int status=0;
	public FileCreationTime() {
		// TODO Auto-generated constructor stub
	
	}
	
	FileCreationTime filec=new FileCreationTime();
	
	static WtsTrigTab wtstrigtab = null;
	WtsTransTab wtstran=null;
	
	public static  final int statusgrey=0;
	public static  final int  statusgreen=1;
	public static  final int  statusred=2;
	public static  final int  statusorange=4;
	public static  final int  statusviolet=3;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	public static String actualStarttime;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	public static String LastModifiedtime;
	
	 private static boolean checkFileExist(Path filePath) throws Exception {
	        try {
				if (Files.exists(filePath)) {
				    System.out.println("Filename: " + filePath.toString());
				    System.out.println("Exist in location!");
				    return true;
				}
				else {
				    System.out.println("Filename: " + filePath.toString());
				    System.out.println("Does not exist in location!");
				   // status=2;
				    return false;
				}
			} catch (Exception e) {
				//status=2;
			    return false;
			}
	    }

	

	private static int sendStatus(int i) {
		// TODO Auto-generated method stub
		return i;
	}

	public static void checkEmptyDirectory(File file, Path filePath) throws Exception {
	        if (file.isDirectory()) {
	            if (file.list().length > 0) {
	                checkFileExist(filePath);
	            } else {
	                System.out
	                        .println("Directory specified does not contain any files!");
	            }
	        } else {
	            System.out.println("Directory specified does not exist!");
	        }
	    }
public static void getLastModifiedDetail(String name) throws Exception	{
	File file = new File("C:/Test/"+name+".txt");
		System.out.println("Name: " + file.getName());
		System.out.println("Absolute path: " + file.getAbsolutePath());
		System.out.println("Size: " + file.length());
		System.out.println("Last modified: " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format((file.lastModified())));
		//System.out.println(file.lastModified());
		
		SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		
		LastModifiedtime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((file.lastModified()));
//				Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((file.lastModified())));
		System.out.println("LastModifiedtime --------- from getLastModifiedDetail"+LastModifiedtime);

				
}
			
	public  static String getCreationDetails(String file_start_path)
    {       
		 File file = new File(file_start_path);
       try{         
        Path p = Paths.get(file.getAbsolutePath());
        BasicFileAttributes view
           = Files.getFileAttributeView(p, BasicFileAttributeView.class)
                  .readAttributes();
        FileTime fileTime=view.creationTime();
        System.out.println("ActualStarttime ----->"+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((file.lastModified()))));
//        ActualStarttime=fileTime.toMillis();
        	//	Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((fileTime.toMillis()))); 
       actualStarttime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((file.lastModified()));
       }
       catch(IOException e){ 
//        e.printStackTrace(); 
        return null;
       }
       
       return actualStarttime; 
   }

	
	public static String getStartfileCreationTime(String name) {
		File file = new File("C:/Test");
		String file_start_path=file.getAbsolutePath()+"/"+name+"_start.txt";
		System.out.println("File name "+name);
		 return getCreationDetails(file_start_path); 
	}
	
	public static String getEndfileCreationTime(String name) {
		File file = new File("C:/Test");
		String file_start_path=file.getAbsolutePath()+"/"+name+"_end.txt";
		System.out.println("end file name"+file_start_path);
		 return getCreationDetails(file_start_path); 
	}
	
	public static String getFailfileCreationTime(String name) {
		File file = new File("C:/Test");
		String file_start_path=file.getPath()+"/"+name+"_fail.txt";
		/*String file_start_path=file.getAbsolutePath()+"/myBatch_fail.txt";*/
		 return getCreationDetails(file_start_path); 
	}
	
	public static int checkFileStatus(String name) {
		File file = new File("C:/Test");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    // Declare today date with format as yyyyMMdd
	    Calendar todayCal = Calendar.getInstance();
	    String todayDate = dateFormat.format(todayCal.getTime());
		Path filePath_start = Paths.get(file.getAbsolutePath()+"/"+name+"_start.txt");
		Path filePath_end = Paths.get(file.getAbsolutePath()+"/"+name+"_end.txt");
		Path filePath_fail = Paths.get(file.getAbsolutePath()+"/"+name+"_fail.txt");
		try {
			boolean startExist=checkFileExist(filePath_start);
			boolean endExist=checkFileExist(filePath_end);
			boolean failExist=checkFileExist(filePath_fail);
			if(startExist && !endExist && !failExist)
			{
				
				return statusorange;
				
			}
			else  if(startExist && endExist)
			{
				return statusgreen;
			}
			else if(startExist && failExist)
			{
				return statusred;
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return statusgrey; 
	}
	
	public static boolean stratFileExist(String name) throws Exception{
		 File file = new File("C:/Test");
		  Path filePath_start = Paths.get(file.getAbsolutePath()+"/"+name+"_start.txt");
		  boolean startExist= checkFileExist(filePath_start);
	    return startExist;
	}
		
	public static boolean endFileExist(String name) throws Exception{
		 File file = new File("C:/Test");
		  Path filePath_end = Paths.get(file.getAbsolutePath()+"/"+name+"_end.txt");
		  boolean endExist=checkFileExist(filePath_end);
	   return endExist;
	}

	public static boolean failFileExist(String name) throws Exception{
		 File file = new File("C:/Test");
		  Path filePath_fail = Paths.get(file.getAbsolutePath()+"/"+name+"_fail.txt");
		  boolean endExist=checkFileExist(filePath_fail);
	  return endExist;
	}
	

	
}
	
	
