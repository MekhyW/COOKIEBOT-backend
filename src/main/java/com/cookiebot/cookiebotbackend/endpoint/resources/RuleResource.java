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

import com.cookiebot.cookiebotbackend.core.domains.Rule;
import com.cookiebot.cookiebotbackend.dao.services.RuleService;

@RestController
@RequestMapping(value = "/rules")
public class RuleResource {

	private final RuleService service;
	
	public RuleResource(RuleService service) {
		this.service = service;
	}
	
	@GetMapping
	public ResponseEntity<List<Rule>> findAll(){
		List<Rule> ruleList = service.findAll();
		return ResponseEntity.ok().body(ruleList);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Rule> findById(@PathVariable String id) {
		Rule ruleList = service.findById(id);
		return ResponseEntity.ok().body(ruleList);
	}
	
	@PostMapping(value="/{id}")
	public ResponseEntity<Rule> insert(@RequestBody Rule rule, @PathVariable String id) {
		service.insert(id, rule);
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
	private ResponseEntity<Void> update(@RequestBody Rule rule, @PathVariable String id) {
		service.update(id, rule);
		return ResponseEntity.ok().build();
	}
}
