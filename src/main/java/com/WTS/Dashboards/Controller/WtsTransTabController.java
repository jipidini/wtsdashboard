package com.WTS.Dashboards.Controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.WTS.Dashboards.Entity.WtsAppTab;
import com.WTS.Dashboards.Entity.WtsTransTab;
import com.WTS.Dashboards.Service.WtsAppTabService;
import com.WTS.Dashboards.Service.WtsTransTabService;

import com.WTS.Dashboards.Utility.DateUtility;
import com.WTS.Dashboards.Utility.TreatmentDate;
import com.WTS.Dashboards.dao.WtsAppTabDao;


@Controller
@RequestMapping("wts")
public class WtsTransTabController {
	@Autowired
	private WtsTransTabService trs;

	@SuppressWarnings("unused")
	@Autowired
	private WtsAppTabService atrs;
	
	@Autowired
	private WtsAppTabDao appDAO;
	
	/*@Autowired
	private WtsTreatTabService trt;*/
	
	@GetMapping("tranupdate/{transId}")
	public ResponseEntity<WtsTransTab> updateTransactionModifiedDetail(@PathVariable("transId") int transId) throws Exception {
		WtsTransTab Trans = trs.getTransactionById(transId);//1
			trs.updateTransactionModifiedDetail(Trans);
		return new ResponseEntity<WtsTransTab>(Trans, HttpStatus.OK);
	}
	
	@GetMapping("procup/date/{processId}")
	public  ResponseEntity<List <WtsTransTab>> getTransactionByProcessId(@PathVariable("processId") int processId) throws Exception
	{
		List <WtsTransTab> finalList=new ArrayList<WtsTransTab>();
		WtsTransTab todayProTxn=trs.getTdyTxnByProcessId(processId,TreatmentDate.getInstance().getTreatmentDate());
	 if(todayProTxn!=null) {
           System.out.println("todaytxn executing");
			finalList.add(todayProTxn);
			trs.updateTransactionModifiedDetail(todayProTxn);
			
			List <WtsAppTab> apps=appDAO.getAllAppsByProcess(processId);
			 if(apps!=null) {
				 Iterator<WtsAppTab> appssItr=apps.iterator();
				 while(appssItr.hasNext()){
				 WtsAppTab app = (WtsAppTab) appssItr.next();
					 String name=app.getName();
					 if(app!=null)// && DateUtility.isAfterNow(app.getStartTime()))
						 {
						 WtsTransTab newAppTrans= new WtsTransTab();
						 newAppTrans.setProcessId(processId);
						 newAppTrans.setApplicationId(app.getApplicationId());
						WtsTransTab existingApp=trs.getTransactionByAppIdProId(app.getApplicationId(), processId,TreatmentDate.getInstance().getTreatmentDate());
						if(existingApp!=null) {
							System.out.println("existing app TXn..");
								trs.updateTransactionModifiedDetail(existingApp);
								finalList.add(existingApp);		
								
								if (existingApp.getStatusId()==2){
									int apId=existingApp.getApplicationId();
									int appId=apId+1;
			
									WtsTransTab existingApp1=trs.getTransactionByAppIdProId(appId, processId,TreatmentDate.getInstance().getTreatmentDate());
									if(existingApp1!=null) {
										existingApp1.setStatusId(2);
										finalList.add(existingApp1);      	
									}
									System.out.println("status 2 set for next app");
									appssItr.next();
									break;
						
								}
								
								}
						
						else {
							trs.addTransaction(newAppTrans,name);
							trs.updateTransactionModifiedDetail(newAppTrans);
							finalList.add(newAppTrans);
							if(newAppTrans.getStatusId()==2){
								int appId=newAppTrans.getApplicationId() + 1;
								WtsTransTab newAppTrans1=trs.getTransactionByAppIdProId(appId, processId,TreatmentDate.getInstance().getTreatmentDate());
								if(newAppTrans1!=null) {
										int ErrStatus=2;
										newAppTrans1.setStatusId(ErrStatus);
										finalList.add(newAppTrans1);
								}
								System.out.println("status 2 set for next app");
								appssItr.next();
								break;
							}
						}
					 }
						
				 	
					 
					
				} 
			 }
		
	 }
	 
	 else {
		
		 WtsTransTab newTrans= new WtsTransTab();
		 newTrans.setProcessId(processId);
		 finalList.add(newTrans);
		 List <WtsAppTab> apps=appDAO.getAllAppsByProcess(processId);
		 
		 if(apps!=null) {
			 Iterator<WtsAppTab> appssItr=apps.iterator();
			 while (appssItr.hasNext()) {
				 WtsAppTab app = (WtsAppTab) appssItr.next();
				 if(app!=null && DateUtility.isAfterNow(app.getStartTime())) {
					 WtsTransTab newAppTrans= new WtsTransTab();
					 newAppTrans.setProcessId(processId);
					 newAppTrans.setApplicationId(app.getApplicationId());
					 finalList.add(newAppTrans);
					 
					 if(newAppTrans.getStatusId()==2){
						 int appId=newAppTrans.getApplicationId() + 1;
							WtsTransTab newAppTrans1=trs.getTransactionByAppIdProId(appId, processId,TreatmentDate.getInstance().getTreatmentDate());
							if(newAppTrans1!=null) {
									//trs.updateTransactionModifiedDetail(newAppTrans1);
									int ErrStatus=2;
									newAppTrans1.setStatusId(ErrStatus);
									finalList.add(newAppTrans1);
							}
							System.out.println("status 2 set for next app");
							appssItr.next();
							break;
					 }
					 
				 }
			} 
		 }
		
		 Iterator<WtsTransTab> trnItr=finalList.iterator();
		 while (trnItr.hasNext()) {
			 String name=null;
			WtsTransTab wtsTransTab = (WtsTransTab) trnItr.next();
			trs.addTransaction(wtsTransTab,name);
		}
		 
		 
		 
	 }
	 return new ResponseEntity<List <WtsTransTab>>(finalList,HttpStatus.OK);
		
	}
	
	@GetMapping("tran/{transId}")
	public ResponseEntity<WtsTransTab> getTransactionById(@PathVariable("transId") int transId) {
		WtsTransTab Trans = trs.getTransactionById(transId);//2
		return new ResponseEntity<WtsTransTab>(Trans, HttpStatus.OK);
	}
	
	
	@GetMapping("tran")
	public ResponseEntity<List<WtsTransTab>> getAllTransaction() {
		List<WtsTransTab> list = trs.getAlltransaction();
		return new ResponseEntity<List<WtsTransTab>>(list, HttpStatus.OK);	
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("tranapp/app/{applicationId}")
	public ResponseEntity<WtsAppTab> getAppById(@PathVariable int applicationId)
	{
		System.out.println("applicationID");
   WtsAppTab ls=(WtsAppTab) atrs.getAppById(applicationId);	
     return new ResponseEntity<WtsAppTab>(ls,HttpStatus.OK);
	}
	
}
