package com.cookiebot.COOKIEBOTbackend.dao.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cookiebot.COOKIEBOTbackend.core.repository.UserRepository;
import com.cookiebot.COOKIEBOTbackend.dao.services.exception.ObjectNotFoundException;
import com.cookiebot.COOKIEBOTbackend.endpoint.domain.User;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repo;
	
	public List<User> findAll() {
		return repo.findAll();
	}

	public User findById(String id) {
		User user = repo.findById(id).orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
		return user;
	}
}
