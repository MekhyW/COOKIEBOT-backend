package com.cookiebot.cookiebotbackend.dao.services;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Service;

import com.cookiebot.cookiebotbackend.core.domains.StickerDatabase;
import com.cookiebot.cookiebotbackend.dao.repository.StickerDatabaseRepository;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.BadRequestException;

@Service
public class StickerDatabaseService {

	private final StickerDatabaseRepository repository;
	private final MongoOperations mongoOperations;

	public StickerDatabaseService(StickerDatabaseRepository repository, MongoOperations mongoOperations) {
		this.repository = repository;
        this.mongoOperations = mongoOperations;
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
