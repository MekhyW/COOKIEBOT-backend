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

import com.cookiebot.cookiebotbackend.core.domain.Configs;
import com.cookiebot.cookiebotbackend.dao.services.ConfigsService;

@RestController
@RequestMapping(value="/configs")
public class ConfigsResource {

	@Autowired
	private ConfigsService service;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Configs>> findAll() {
		List<Configs> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Configs> findById(@PathVariable String id) {
		Configs obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Configs> insert(@RequestBody Configs configs) {
		configs = service.insert(configs);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(configs.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	private ResponseEntity<Void> update(@RequestBody Configs configs, @PathVariable String id) {
		configs.setId(id);
		configs = service.update(configs);
		return ResponseEntity.noContent().build();
	}
}
