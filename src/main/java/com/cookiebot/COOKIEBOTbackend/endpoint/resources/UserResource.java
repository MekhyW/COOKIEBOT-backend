package com.cookiebot.COOKIEBOTbackend.endpoint.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cookiebot.COOKIEBOTbackend.endpoint.domain.User;

@RestController
@RequestMapping(value="/users")
public class UserResource {

	// This is a test to see if this API actually works properly
	// Accessible with localhost:8080/users
	@GetMapping
	public ResponseEntity<List<User>> findAll() {
		User usuarioteste63 = new User("1", "Usuário Teste");
		User uwu = new User("2", "OwO");
		User owo = new User("3", "UwU");
		List<User> list = new ArrayList<>();
		list.addAll(Arrays.asList(usuarioteste63,owo,uwu));
		return ResponseEntity.ok().body(list);
	}
}
