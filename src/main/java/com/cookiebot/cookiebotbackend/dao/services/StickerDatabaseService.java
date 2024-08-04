package com.cookiebot.cookiebotbackend.dao.services;

import com.cookiebot.cookiebotbackend.dao.services.exceptions.ObjectNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.cookiebot.cookiebotbackend.core.domains.StickerDatabase;
import com.cookiebot.cookiebotbackend.dao.repository.StickerDatabaseRepository;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.BadRequestException;

import java.util.List;

@Service
public class StickerDatabaseService {

	private final StickerDatabaseRepository repository;

	public StickerDatabaseService(final StickerDatabaseRepository repository) {
		this.repository = repository;
    }

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
		if (stickerDatabase.getId() == null) {
			throw new BadRequestException("'id' Must Not Be Null");	
		}

		try {
			return repository.insert(stickerDatabase);
		} catch (DuplicateKeyException e) {
			throw new BadRequestException("ID " + stickerDatabase.getId() + " already exists");
		}
	}
}
