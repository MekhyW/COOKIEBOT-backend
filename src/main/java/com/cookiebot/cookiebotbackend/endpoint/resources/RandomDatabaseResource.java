package com.cookiebot.cookiebotbackend.endpoint.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cookiebot.cookiebotbackend.core.domains.RandomDatabase;
import com.cookiebot.cookiebotbackend.dao.services.RandomDatabaseService;

@RestController
@RequestMapping(value = "/randomdatabase")
public class RandomDatabaseResource {

	private final RandomDatabaseService service;
	
	@Autowired
	public RandomDatabaseResource(RandomDatabaseService service) {
		this.service = service;
	}
	
	@GetMapping
	public ResponseEntity<RandomDatabase> findAll() {
		RandomDatabase randomArray = service.getRandom();
		return ResponseEntity.ok().body(randomArray);
	}
	
	@PostMapping
	public ResponseEntity<RandomDatabase> insert(@RequestBody RandomDatabase randomDatabase) {
		service.insert(randomDatabase);
		return ResponseEntity.ok().build();
	}
}
