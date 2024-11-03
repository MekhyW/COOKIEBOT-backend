package com.cookiebot.cookiebotbackend.endpoint.resources;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cookiebot.cookiebotbackend.core.domains.Welcome;
import com.cookiebot.cookiebotbackend.dao.services.WelcomeService;

@RestController
@RequestMapping(value = "/welcomes")
public class WelcomeResource {

	private final WelcomeService service;
	
	public WelcomeResource(WelcomeService service) {
		this.service = service;
	}
	
	@GetMapping
	public ResponseEntity<List<Welcome>> findAll(){
		List<Welcome> welcomeList = service.findAll();
		return ResponseEntity.ok().body(welcomeList);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Welcome> findById(@PathVariable String id) {
		Welcome welcomeList = service.findById(id);
		return ResponseEntity.ok().body(welcomeList);
	}
	
	@PostMapping(value="/{id}")
	public ResponseEntity<Welcome> insert(@RequestBody Welcome welcome, @PathVariable String id) {
		service.insert(id, welcome);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(id).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Void> update(@RequestBody Welcome welcome, @PathVariable String id) {
		service.update(id, welcome);
		return ResponseEntity.ok().build();
	}
}
