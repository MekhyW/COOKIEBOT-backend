package com.cookiebot.cookiebotbackend.endpoint.resources;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cookiebot.cookiebotbackend.core.domains.Blacklist;
import com.cookiebot.cookiebotbackend.core.domains.Rule;
import com.cookiebot.cookiebotbackend.dao.services.BlacklistService;

@RestController
@RequestMapping(value = "/blacklist")
public class BlacklistResource {

	private final BlacklistService service;
	
	public BlacklistResource(BlacklistService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<List<Blacklist>> findAll(){
		List<Blacklist> blacklist = service.findAll();
		return ResponseEntity.ok().body(blacklist);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Blacklist> findById(@PathVariable String id) {
		Blacklist blacklist = service.findById(id);
		return ResponseEntity.ok().body(blacklist);
	}
	
	@PostMapping(value="/{id}")
	public ResponseEntity<Rule> insert(@PathVariable String id) {
		service.insert(id);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(id).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}
}
