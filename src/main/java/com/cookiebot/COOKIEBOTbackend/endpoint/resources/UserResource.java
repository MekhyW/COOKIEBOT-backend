package com.cookiebot.COOKIEBOTbackend.endpoint.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cookiebot.COOKIEBOTbackend.dao.services.UserService;
import com.cookiebot.COOKIEBOTbackend.endpoint.domain.User;

@RestController
@RequestMapping(value="/users")
public class UserResource {

	@Autowired
	private UserService service;

	// Accessible with localhost:8080/users
	@GetMapping
	public ResponseEntity<List<User>> findAll() {
		List<User> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
}
