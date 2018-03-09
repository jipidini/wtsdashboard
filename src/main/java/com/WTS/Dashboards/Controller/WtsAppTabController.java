package com.WTS.Dashboards.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.WTS.Dashboards.Entity.WtsAppTab;
import com.WTS.Dashboards.Service.WtsAppTabService;

@Controller
@RequestMapping("wts")
public class WtsAppTabController{
	@Autowired
	private WtsAppTabService  appService;
	
	@GetMapping("app/{applicationId}")
	
	public ResponseEntity<WtsAppTab> getArticleById(@PathVariable("applicationId") int applicationId) {
		WtsAppTab application = appService.getAppById(applicationId);
		return new ResponseEntity<WtsAppTab>(application, HttpStatus.OK);
	}
	
	@GetMapping("app")
	public ResponseEntity<List<WtsAppTab>> getAllApp() {
		List<WtsAppTab> list = appService.getAllApps();
		return new ResponseEntity<List<WtsAppTab>>(list, HttpStatus.OK);	
	}

	/*@PostMapping("application}")
	public ResponseEntity<Void> addApp(@RequestBody wtsAppTab application, UriComponentsBuilder builder) {
        boolean flag = appService.addApp(application);
        if (flag == false) {
        	return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/application/{id}").buildAndExpand(application.getApplicationId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}*/
}
