package com.cookiebot.cookiebotbackend.dao.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cookiebot.cookiebotbackend.core.domains.Blacklist;
import com.cookiebot.cookiebotbackend.dao.repository.BlacklistRepository;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.BadRequestException;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.ObjectNotFoundException;

@Service
public class BlacklistService {

	@Autowired
	private BlacklistRepository repository;
	
	public List<Blacklist> findAll(){
		return repository.findAll();
	}
	
	public Blacklist findById(String id) {
		Blacklist blacklist = repository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Object Not Found"));
		return blacklist;
	}
	
	public Blacklist insert(String id) {
		
		if (repository.findById(id).orElse(null) != null) {
			throw new BadRequestException("ID Already Exists");
		} 
		
		Blacklist blacklist = new Blacklist();
		blacklist.setId(id);
		return repository.insert(blacklist);
	}
	
	public void delete(String id) {
		try {
			repository.deleteById(id);
		} catch (Exception e) {
			throw new BadRequestException("ID Must Not Be Null");
		}
	}
}
