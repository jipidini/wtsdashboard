package com.WTS.Dashboards.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.WTS.Dashboards.Entity.WtsAppTab;
import com.WTS.Dashboards.Entity.WtsNewEtaTab;
import com.WTS.Dashboards.Service.WtsNewEtaTabService;

@Controller
@RequestMapping("wts")
public class WtsNewEtaTabController {

	@Autowired
	private WtsNewEtaTabService  etaService;
	
	@GetMapping("eta")
	public ResponseEntity<List<WtsNewEtaTab>> getAllEta() {
		List<WtsNewEtaTab> list = etaService.getAllEta();
		return new ResponseEntity<List<WtsNewEtaTab>>(list, HttpStatus.OK);	
	}
}
