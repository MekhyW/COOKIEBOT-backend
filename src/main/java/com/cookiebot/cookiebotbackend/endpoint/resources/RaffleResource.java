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

import com.cookiebot.cookiebotbackend.core.domains.Raffle;
import com.cookiebot.cookiebotbackend.core.domains.RaffleParticipant;
import com.cookiebot.cookiebotbackend.dao.services.RaffleService;

@RestController
@RequestMapping(value = "/raffles")
public class RaffleResource {

	private final RaffleService service;
	
	@Autowired
	public RaffleResource(RaffleService service) {
		this.service = service;
	}
	
	// Manage Raffle
	@GetMapping
	public ResponseEntity<List<Raffle>> findAll() {
		List<Raffle> raffleList = service.findAll();
		return ResponseEntity.ok().body(raffleList);
	}
	
	@GetMapping(value = "/{name}")
	public ResponseEntity<Raffle> findByName(@PathVariable String name) {
		Raffle raffleList = service.findByName(name);
		return ResponseEntity.ok().body(raffleList);
	}
	
	@PostMapping(value = "/{name}")
	public ResponseEntity<Raffle> insert(@RequestBody Raffle raffle, @PathVariable String name) {
		raffle.setName(name);

		service.insert(raffle);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{name}").buildAndExpand(name).toUri();

		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value= "/{name}")
	public ResponseEntity<Void> update(@RequestBody Raffle raffle, @PathVariable String name){
		service.update(name, raffle);	
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping(value = "/{name}")
	public ResponseEntity<Void> delete(@PathVariable String name) {
		service.delete(name);
		return ResponseEntity.ok().build();
	}

	// Manage Participants from Raffle	
	@GetMapping(value = "/{name}/participants")
	public ResponseEntity<List<RaffleParticipant>> findParticipants(@PathVariable String name) {
		// todo
		List<RaffleParticipant> participantList = service.findParticipants(name);
		return ResponseEntity.ok().body(participantList);
	}
	
	@PostMapping(value = "/{name}/participants")
	public ResponseEntity<Void> insertParticipant(@PathVariable String name, @RequestBody RaffleParticipant participant) {
		service.insertParticipant(name, participant);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping(value = "/{name}/participants")
	public ResponseEntity<Void> deleteParticipant(@PathVariable String name, @RequestBody RaffleParticipant participant) {
		service.deleteParticipant(name, participant);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping(value = "/{name}/participants")
	public ResponseEntity<Void> updateParticipant(@PathVariable String name, @RequestBody RaffleParticipant participant) {
		service.updateParticipant(name, participant);
		return ResponseEntity.ok().build();
	}	
}
