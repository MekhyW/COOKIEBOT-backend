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

import com.cookiebot.cookiebotbackend.core.domain.Config;
import com.cookiebot.cookiebotbackend.dao.services.ConfigService;

@RestController
@RequestMapping(value="/configs")
public class ConfigResource {

	@Autowired
	private ConfigService service;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Config>> findAll() {
		List<Config> configList = service.findAll();
		return ResponseEntity.ok().body(configList);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Config> findById(@PathVariable String id) {
		Config configList = service.findById(id);
		return ResponseEntity.ok().body(configList);
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.POST)
	public ResponseEntity<Config> insert(@RequestBody Config config, @PathVariable String id) {
		config.setId(id);
		service.insert(config);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(config.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	private ResponseEntity<Void> update(@RequestBody Config config, @PathVariable String id) {
		config.setId(id);
		service.update(config);
		return ResponseEntity.ok().build();
	}
}
