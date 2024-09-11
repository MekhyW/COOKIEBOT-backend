package com.cookiebot.cookiebotbackend.endpoint.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cookiebot.cookiebotbackend.core.domains.StickerDatabase;
import com.cookiebot.cookiebotbackend.dao.services.StickerDatabaseService;

@RestController
@RequestMapping(value = "/stickerdatabase")
public class StickerDatabaseResource {
	
	private final StickerDatabaseService service;
	
	@Autowired
	public StickerDatabaseResource(StickerDatabaseService service) {
		this.service = service;
	}
	
	@GetMapping
	public ResponseEntity<StickerDatabase> findAll() {
		StickerDatabase stickerArray = service.getRandom();
		return ResponseEntity.ok().body(stickerArray);
	}
	
	@PostMapping
	public ResponseEntity<StickerDatabase> insert(@RequestBody StickerDatabase stickerDatabase) {
		service.insert(stickerDatabase);
		return ResponseEntity.ok().build();
	}
}
