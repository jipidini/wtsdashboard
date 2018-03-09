package com.WTS.Dashboards.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.WTS.Dashboards.Entity.WtsUserTab;
import com.WTS.Dashboards.Service.WtsUserTabService;

@Controller
@RequestMapping("wts")
public class WtsUserTabController {
	@Autowired
	private WtsUserTabService use;
	
	@GetMapping("user/{userId}")
	public ResponseEntity<WtsUserTab> getUserById(@PathVariable("userId") int userId) {
		WtsUserTab list = use.getUserById(userId);
		return new ResponseEntity<WtsUserTab>(list, HttpStatus.OK);
	}
	
	@GetMapping("user")
	public ResponseEntity<List<WtsUserTab>> getAllUser() {
		List<WtsUserTab> list = use.getAlluser();
		return new ResponseEntity<List<WtsUserTab>>(list, HttpStatus.OK);	
	}
}
