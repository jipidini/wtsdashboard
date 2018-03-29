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
import com.WTS.Dashboards.Service.EmailService;
import com.WTS.Dashboards.Service.WtsAppTabService;
import com.WTS.Dashboards.Service.WtsTransTabService;

import com.WTS.Dashboards.Utility.DateUtility;
import com.WTS.Dashboards.Utility.TreatmentDate;
import com.WTS.Dashboards.dao.WtsAppTabDao;
import com.WTS.Dashboards.dao.WtsNewEtaTabDao;
import com.fasterxml.jackson.annotation.JsonProperty;


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
	
	@Autowired
	private WtsNewEtaTabDao etaDAO;
	
	@Autowired
    private EmailService emailService;

	
	public static final int STATUS_IN_PROGRESS=4;
	public static final int STATUS_SUCCESS=1;
	public static final int STATUS_YET_TO_START=0;
	public static final int STATUS_FAILURE=2;
	public static final int STATUS_DELAYED=3;
	
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
		
		List  finalList=trs.fetchAllTxns(processId);
		finalList.addAll(etaDAO.getAllEta());
	 
	 return new ResponseEntity<List <WtsTransTab>>(finalList,HttpStatus.OK);
		
	}
	
	
	@GetMapping("testSMS/{number}")

	public  ResponseEntity<List <WtsTransTab>> testSMS(@PathVariable("number") String number) throws Exception
	{
		emailService.sendREDalertSMS(number);
		
	 return new ResponseEntity<List <WtsTransTab>>(new ArrayList<>(),HttpStatus.OK);
		
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
