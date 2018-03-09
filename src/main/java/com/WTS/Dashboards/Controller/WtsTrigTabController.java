package com.WTS.Dashboards.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.WTS.Dashboards.Entity.WtsTrigTab;
import com.WTS.Dashboards.Service.WtsTrigTabService;




@Controller
@RequestMapping("wts")
public class WtsTrigTabController {
	@Autowired
	private WtsTrigTabService tri;
	
	@GetMapping("trig/{trigId}")
	public ResponseEntity<WtsTrigTab> getTrigById(@PathVariable("trigId") int trigId) {
		WtsTrigTab list = tri.getTriggerById(trigId);
		return new ResponseEntity<WtsTrigTab>(list, HttpStatus.OK);
	}
	
	@GetMapping("trig")
	public ResponseEntity<List<WtsTrigTab>> getAllTrigger() {
		List<WtsTrigTab> list = tri.getAlltrig();
		return new ResponseEntity<List<WtsTrigTab>>(list, HttpStatus.OK);	
	}
}



