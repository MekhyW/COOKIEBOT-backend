package com.cookiebot.cookiebotbackend.dao.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cookiebot.cookiebotbackend.core.domain.Welcome;
import com.cookiebot.cookiebotbackend.dao.repository.WelcomeRepository;
import com.cookiebot.cookiebotbackend.dao.services.exception.BadRequestException;
import com.cookiebot.cookiebotbackend.dao.services.exception.ObjectNotFoundException;

@Service
public class WelcomeService {

	@Autowired
	private WelcomeRepository repository;
	
	public List<Welcome> findAll(){
		return repository.findAll();
	}
	
	public Welcome findById(String id) {
		Welcome welcome = repository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Object Not Found"));
		return welcome;
	}
	
	public Welcome insert(String id, Welcome welcome) {
		
		if (id == null) {
			throw new BadRequestException("ID Must Not Be Null");
		}
		
		Welcome findId = repository.findById(id).orElse(null);
		
		if (findId == null) {
			welcome.setId(id);
			return repository.insert(welcome);
		} else {
			throw new BadRequestException("ID Already Exists");
		}
	}
	
	public void delete(String id) {
		
		if (id == null) {
			throw new BadRequestException("ID Must Not Be Null");
		}
		
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
