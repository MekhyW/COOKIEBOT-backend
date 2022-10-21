package com.cookiebot.cookiebotbackend.endpoint.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cookiebot.cookiebotbackend.core.domain.Welcome;
import com.cookiebot.cookiebotbackend.dao.services.WelcomeService;

@RestController
@RequestMapping(value="/welcomes")
public class WelcomeResource {

	@Autowired
	WelcomeService service;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Welcome>> findAll(){
		List<Welcome> welcomeList = service.findAll();
		return ResponseEntity.ok().body(welcomeList);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Welcome> findById(@PathVariable String id) {
		Welcome welcomeList = service.findById(id);
		return ResponseEntity.ok().body(welcomeList);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.POST)
	public ResponseEntity<Welcome> insert(@RequestBody Welcome welcome, @PathVariable String id) {
		service.insert(id, welcome);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(id).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	private ResponseEntity<Void> update(@RequestBody Welcome welcome, @PathVariable String id) {
		service.update(id, welcome);
		return ResponseEntity.ok().build();
	}
}
