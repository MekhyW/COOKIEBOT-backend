package com.cookiebot.cookiebotbackend.dao.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cookiebot.cookiebotbackend.core.domains.Blacklist;
import com.cookiebot.cookiebotbackend.dao.repository.BlacklistRepository;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.BadRequestException;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.ObjectNotFoundException;

import java.util.List;

@Service
public class BlacklistService {
	
	private final BlacklistRepository repository;
	
	@Autowired
	public BlacklistService(BlacklistRepository repository) {
		this.repository = repository;
	}
	
	public List<Blacklist> findAll() {
		return repository.findAll();
	}
	
	public Blacklist findById(String id) {
		return repository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Object Not Found"));
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
		repository.deleteById(id);
	}
}