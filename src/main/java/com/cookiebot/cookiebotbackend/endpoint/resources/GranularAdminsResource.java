package com.cookiebot.cookiebotbackend.endpoint.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cookiebot.cookiebotbackend.core.domain.GranularAdmins;
import com.cookiebot.cookiebotbackend.dao.services.GranularAdminsService;

@RestController
@RequestMapping(value="/granularadmins")
public class GranularAdminsResource {

	@Autowired
	private GranularAdminsService service;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<GranularAdmins>> findAll() {
		List<GranularAdmins> list = service.findAll();
		return ResponseEntity.ok().body(list);		
	}
}
