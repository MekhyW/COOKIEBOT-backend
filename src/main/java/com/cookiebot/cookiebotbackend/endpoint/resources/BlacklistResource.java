package com.cookiebot.cookiebotbackend.endpoint.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cookiebot.cookiebotbackend.core.domains.Blacklist;
import com.cookiebot.cookiebotbackend.core.domains.Rule;
import com.cookiebot.cookiebotbackend.dao.services.BlacklistService;

@RestController
@RequestMapping(value = "/blacklist")
public class BlacklistResource {

	@Autowired
	private BlacklistService service;

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Blacklist>> findAll(){
		List<Blacklist> blacklist = service.findAll();
		return ResponseEntity.ok().body(blacklist);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Blacklist> findById(@PathVariable String id) {
		Blacklist blacklist = service.findById(id);
		return ResponseEntity.ok().body(blacklist);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.POST)
	public ResponseEntity<Rule> insert(@PathVariable String id) {
		service.insert(id);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(id).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}
}
