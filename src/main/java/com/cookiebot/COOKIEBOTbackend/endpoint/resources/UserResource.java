package com.cookiebot.COOKIEBOTbackend.endpoint.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cookiebot.COOKIEBOTbackend.dao.services.UserService;
import com.cookiebot.COOKIEBOTbackend.endpoint.domain.User;
import com.cookiebot.COOKIEBOTbackend.endpoint.dto.UserDTO;

@RestController
@RequestMapping(value="/users")
public class UserResource {

	@Autowired
	private UserService service;

	// Accessible with localhost:8080/users
	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll() {
		List<User> list = service.findAll();
		List<UserDTO> listDto = list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
}
