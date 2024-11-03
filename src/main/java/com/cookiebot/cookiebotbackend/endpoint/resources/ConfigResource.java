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

import com.cookiebot.cookiebotbackend.core.domains.Config;
import com.cookiebot.cookiebotbackend.dao.services.ConfigService;

@RestController
@RequestMapping(value = "/configs")
public class ConfigResource {

	private final ConfigService service;
	
	public ConfigResource(ConfigService service) {
		this.service = service;
	}
	
	@GetMapping
	public ResponseEntity<List<Config>> findAll() {
		List<Config> configList = service.findAll();
		return ResponseEntity.ok().body(configList);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Config> findById(@PathVariable String id) {
		Config configList = service.findById(id);
		return ResponseEntity.ok().body(configList);
	}
	
	@PostMapping(value="/{id}")
	public ResponseEntity<Config> insert(@RequestBody Config config, @PathVariable String id) {
		service.insert(id, config);
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
	private ResponseEntity<Void> update(@RequestBody Config config, @PathVariable String id) {
		service.update(id, config);
		return ResponseEntity.ok().build();
	}
}
