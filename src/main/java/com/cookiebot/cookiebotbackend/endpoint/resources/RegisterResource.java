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

import com.cookiebot.cookiebotbackend.core.domain.Register;
import com.cookiebot.cookiebotbackend.core.domain.UserRegister;
import com.cookiebot.cookiebotbackend.dao.services.RegisterService;

@RestController
@RequestMapping(value="/registers")
public class RegisterResource {

	@Autowired
	private RegisterService service;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Register>> findAll() {
		List<Register> registerList = service.findAll();
		return ResponseEntity.ok().body(registerList);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Register> findById(@PathVariable String id) {
		Register registerList = service.findById(id);
		return ResponseEntity.ok().body(registerList);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.POST)
	public ResponseEntity<Register> insert(@RequestBody Register register, @PathVariable String id) {
		register.setId(id);
		service.insert(register);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(register.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}/users", method=RequestMethod.POST)
	public ResponseEntity<Void> insertUser(@PathVariable String id, @RequestBody UserRegister userRegister) {
		service.insertUser(id, userRegister);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}/users", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteUser(@PathVariable String id, @RequestBody UserRegister userRegister) {
		service.deleteUser(id, userRegister);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}/users", method=RequestMethod.PUT)
	public ResponseEntity<Void> updateUser(@PathVariable String id, @RequestBody UserRegister userRegister) {
		service.updateUser(id, userRegister);
		return ResponseEntity.noContent().build();
	}
}
