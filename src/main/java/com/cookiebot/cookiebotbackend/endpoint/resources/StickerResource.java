package com.cookiebot.cookiebotbackend.endpoint.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.cookiebot.cookiebotbackend.core.domains.Sticker;
import com.cookiebot.cookiebotbackend.dao.services.StickerService;

@RestController
@RequestMapping(value = "/stickers")
public class StickerResource {

	@Autowired
	private StickerService service;
	
	@GetMapping
	public ResponseEntity<List<Sticker>> findAll(){
		List<Sticker> stickerList = service.findAll();
		return ResponseEntity.ok().body(stickerList);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Sticker> findById(@PathVariable String id) {
		Sticker stickerList = service.findById(id);
		return ResponseEntity.ok().body(stickerList);
	}
	
	@PostMapping(value="/{id}")
	public ResponseEntity<Sticker> insert(@RequestBody Sticker sticker, @PathVariable String id) {
		service.insert(id, sticker);
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
	private ResponseEntity<Void> update(@RequestBody Sticker sticker, @PathVariable String id) {
		service.update(id, sticker);
		return ResponseEntity.ok().build();
	}
}
