package com.cookiebot.cookiebotbackend.endpoint.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cookiebot.cookiebotbackend.core.domains.RandomDatabase;
import com.cookiebot.cookiebotbackend.dao.services.RandomDatabaseService;

@RestController
@RequestMapping(value = "/randomdatabase")
public class RandomDatabaseResource {

	@Autowired
	private RandomDatabaseService service;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<RandomDatabase> findAll() {
		RandomDatabase randomArray = service.getRandom();
		return ResponseEntity.ok().body(randomArray);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<RandomDatabase> insert(@RequestBody RandomDatabase randomDatabase) {
		service.insert(randomDatabase);
		return ResponseEntity.ok().build();
	}
}
