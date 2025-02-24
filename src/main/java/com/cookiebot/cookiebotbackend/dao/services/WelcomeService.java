package com.cookiebot.cookiebotbackend.dao.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cookiebot.cookiebotbackend.core.domains.Welcome;
import com.cookiebot.cookiebotbackend.dao.repository.WelcomeRepository;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.BadRequestException;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.ObjectNotFoundException;

@Service
public class WelcomeService {

	private final WelcomeRepository repository;
	
	public WelcomeService(WelcomeRepository repository) {
		this.repository = repository;
	}
	
	public List<Welcome> findAll(){
		return repository.findAll();
	}
	
	public Welcome findById(String id) {
		return repository.findById(id).orElseThrow(ObjectNotFoundException::new);
	}
	
	public Welcome insert(String id, Welcome welcome) {
		if (repository.findById(id).orElse(null) != null) {
			throw new BadRequestException("ID Already Exists");
		} 
		
		welcome.setId(id);
		return repository.insert(welcome);
	}
	
	public void delete(String id) {
		repository.deleteById(id);
	}
	
	public Welcome update(String id, Welcome welcome) {
		Welcome newWelcome = repository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Object Not Found"));
		welcome.setId(id);
		updateWelcome(newWelcome, welcome);
		return repository.save(newWelcome);
	}

	private void updateWelcome(Welcome newWelcome, Welcome welcome) {
		if (welcome.getMessage() != null) {
			newWelcome.setMessage(welcome.getMessage());
		}
	}
}
