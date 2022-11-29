package com.cookiebot.cookiebotbackend.endpoint.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cookiebot.cookiebotbackend.core.domains.RandomDatabase;
import com.cookiebot.cookiebotbackend.core.domains.StickerDatabase;
import com.cookiebot.cookiebotbackend.dao.services.StickerDatabaseService;

@RestController
@RequestMapping(value = "/stickerdatabase")
public class StickerDatabaseResource {
	
	@Autowired
	private StickerDatabaseService service;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<StickerDatabase> findAll() {
		StickerDatabase stickerArray = service.getRandom();
		return ResponseEntity.ok().body(stickerArray);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<StickerDatabase> insert(@RequestBody StickerDatabase stickerDatabase) {
		service.insert(stickerDatabase);
		return ResponseEntity.ok().build();
	}
}
