package com.WTS.Dashboards.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.WTS.Dashboards.DTO.ApplicationMappingDTO;
import com.WTS.Dashboards.DTO.ProcessDTO;
import com.WTS.Dashboards.Entity.WtsProcessTab;
import com.WTS.Dashboards.Service.WtsProcessTabService;

@Controller
@RequestMapping("wts")
public class WtsProcessTabController {
	@Autowired
	private WtsProcessTabService proservice;
	
@GetMapping("process/{processId}")
	
	public ResponseEntity<WtsProcessTab> getArticleById(@PathVariable("processId") int processId) {
		WtsProcessTab Process = proservice.getProcessById(processId);
		return new ResponseEntity<WtsProcessTab>(Process, HttpStatus.OK);
	}
	
	@GetMapping("process")
	public ResponseEntity<List<ProcessDTO>> getAllProcess() {
		List<ProcessDTO> list = proservice.getAllprocessDTOs();
		return new ResponseEntity<List<ProcessDTO>>(list, HttpStatus.OK);	
	}
	
	@GetMapping("application/{applicationId}/{processId}/{parentId}")
	public ResponseEntity<List<ApplicationMappingDTO>> getAllApplications(@PathVariable("applicationId") int applicationId,@PathVariable("processId") int processId,@PathVariable("parentId") int parentId) {
		List<ApplicationMappingDTO> list = proservice.getAllDTOsForApplication(applicationId,processId,parentId); 
		return new ResponseEntity<List<ApplicationMappingDTO>>(list, HttpStatus.OK);	
	}
	
//	@GetMapping("procupdate/{processId}")
//	public ResponseEntity<WtsTransTab> updateTransactionModifiedDetail(@PathVariable("processId") int processId) throws Exception {
////		WtsTransTab Trans = proservice.getTransactionById(processId);
//		proservice.updateTransactionModifiedDetail(Trans);
//		return new ResponseEntity<WtsTransTab>(Trans, HttpStatus.OK);
//	}
}
