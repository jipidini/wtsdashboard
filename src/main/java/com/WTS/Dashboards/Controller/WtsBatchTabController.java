package com.WTS.Dashboards.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.WTS.Dashboards.Entity.WtsBatchTab;
import com.WTS.Dashboards.Service.WtsBatchTabService;

@Controller
@RequestMapping("wts")

public class WtsBatchTabController {
@Autowired
private WtsBatchTabService batService;
@GetMapping("batch/{batchId}")
public ResponseEntity<WtsBatchTab> getArticleById(@PathVariable("batchId") int batchId) {
	WtsBatchTab batch = batService.getBatchById(batchId);
	return new ResponseEntity<WtsBatchTab>(batch, HttpStatus.OK);
 
}

@GetMapping("batch")
public ResponseEntity<List<WtsBatchTab>> getAllBatch() {
	List<WtsBatchTab> list = batService.getAllBatches();
	return new ResponseEntity<List<WtsBatchTab>>(list, HttpStatus.OK);	
}



}
