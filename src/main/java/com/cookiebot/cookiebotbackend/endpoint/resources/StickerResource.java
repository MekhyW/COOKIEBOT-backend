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

import com.cookiebot.cookiebotbackend.core.domains.Sticker;
import com.cookiebot.cookiebotbackend.dao.services.StickerService;

@RestController
@RequestMapping(value = "/stickers")
public class StickerResource {

	@Autowired
	private StickerService service;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Sticker>> findAll(){
		List<Sticker> stickerList = service.findAll();
		return ResponseEntity.ok().body(stickerList);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Sticker> findById(@PathVariable String id) {
		Sticker stickerList = service.findById(id);
		return ResponseEntity.ok().body(stickerList);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.POST)
	public ResponseEntity<Sticker> insert(@RequestBody Sticker sticker, @PathVariable String id) {
		service.insert(id, sticker);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(id).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	private ResponseEntity<Void> update(@RequestBody Sticker sticker, @PathVariable String id) {
		service.update(id, sticker);
		return ResponseEntity.ok().build();
	}
}
