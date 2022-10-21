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

import com.cookiebot.cookiebotbackend.core.domain.Rule;
import com.cookiebot.cookiebotbackend.dao.services.RuleService;

@RestController
@RequestMapping(value="/rules")
public class RuleResource {

	@Autowired
	private RuleService service;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Rule>> findAll(){
		List<Rule> ruleList = service.findAll();
		return ResponseEntity.ok().body(ruleList);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Rule> findById(@PathVariable String id) {
		Rule ruleList = service.findById(id);
		return ResponseEntity.ok().body(ruleList);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.POST)
	public ResponseEntity<Rule> insert(@RequestBody Rule rule, @PathVariable String id) {
		rule.setId(id);
		service.insert(rule);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(rule.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	private ResponseEntity<Void> update(@RequestBody Rule rule, @PathVariable String id) {
		rule.setId(id);
		service.update(rule);
		return ResponseEntity.noContent().build();
	}
}
