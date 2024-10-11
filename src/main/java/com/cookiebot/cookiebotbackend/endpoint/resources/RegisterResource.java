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

import com.cookiebot.cookiebotbackend.core.domains.Register;
import com.cookiebot.cookiebotbackend.core.domains.UserRegister;
import com.cookiebot.cookiebotbackend.dao.services.RegisterService;

@RestController
@RequestMapping(value = "/registers")
public class RegisterResource {

	private final RegisterService service;
	
	public RegisterResource(RegisterService service) {
		this.service = service;
	}
	
	// Manage Register
	@GetMapping
	public ResponseEntity<List<Register>> findAll() {
		List<Register> registerList = service.findAll();
		return ResponseEntity.ok().body(registerList);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Register> findById(@PathVariable String id) {
		Register registerList = service.findById(id);
		return ResponseEntity.ok().body(registerList);
	}
	
	@PostMapping(value="/{id}")
	public ResponseEntity<Register> insert(@RequestBody Register register, @PathVariable String id) {
		service.insert(id, register);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(id).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	
	// Manage Users from Register
	@GetMapping(value = "/{id}/users")
	public ResponseEntity<List<UserRegister>> findUsers(@PathVariable String id) {
	List<UserRegister> userList = service.findUsers(id);
	return ResponseEntity.ok().body(userList);
	}
	
	@PostMapping(value="/{id}/users")
	public ResponseEntity<Void> insertUser(@PathVariable String id, @RequestBody UserRegister user) {
		service.insertUser(id, user);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping(value="/{id}/users")
	public ResponseEntity<Void> deleteUser(@PathVariable String id, @RequestBody UserRegister user) {
		service.deleteUser(id, user);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping(value="/{id}/users")
	public ResponseEntity<Void> updateUser(@PathVariable String id, @RequestBody UserRegister user) {
		service.updateUser(id, user);
		return ResponseEntity.ok().build();
	}
}
