package com.cookiebot.cookiebotbackend.dao.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cookiebot.cookiebotbackend.core.domains.StickerDatabase;
import com.cookiebot.cookiebotbackend.dao.repository.StickerDatabaseRepository;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.BadRequestException;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.ObjectNotFoundException;

@Service
public class StickerDatabaseService {

	@Autowired
	private StickerDatabaseRepository repository;
	
	public StickerDatabase getRandom(){
		List<StickerDatabase> stickerList = repository.findAll();

		try {
			StickerDatabase stickerArray = stickerList.get((int)(stickerList.size() * Math.random()));
			return stickerArray;
		} catch (Exception e) {
			throw new ObjectNotFoundException("Object Not Found");
		}
	}
	
	public StickerDatabase insert(StickerDatabase stickerDatabase) {
		
		if (repository.findAll().size() >= 1000 ) {
			this.delete();
		}
		
		if (stickerDatabase.getId() == null) {
			throw new BadRequestException("'id' Must Not Be Null");	
		} 
		
		if (repository.findById(stickerDatabase.getId()).orElse(null) != null) {
			throw new BadRequestException("ID Already Exists");	
		} 
		
		return repository.insert(stickerDatabase);
	}
	
	private void delete() {
		List<StickerDatabase> stickerList = repository.findAll();
		repository.deleteById(stickerList.get(0).getId());
	}
}
