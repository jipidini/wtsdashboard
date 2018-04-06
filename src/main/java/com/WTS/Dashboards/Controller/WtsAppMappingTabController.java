package com.WTS.Dashboards.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.WTS.Dashboards.Entity.WtsAppMappingTab;
import com.WTS.Dashboards.Service.WtsAppMappingTabService;



@Controller
@RequestMapping("wts")

public class WtsAppMappingTabController {
@Autowired
private WtsAppMappingTabService mappingService;
@GetMapping("appMapping/{mappingId}")
public ResponseEntity<WtsAppMappingTab> getArticleById(@PathVariable("mappingId") int mappingId) {
	WtsAppMappingTab appMapping = mappingService.getAppMappingById(mappingId);
	return new ResponseEntity<WtsAppMappingTab>(appMapping, HttpStatus.OK);
 
}

@GetMapping("appMapping")
public ResponseEntity<List<WtsAppMappingTab>> getAllBatch() {
	List<WtsAppMappingTab> list = mappingService.getAllAppMappings();
	return new ResponseEntity<List<WtsAppMappingTab>>(list, HttpStatus.OK);	
}



}
