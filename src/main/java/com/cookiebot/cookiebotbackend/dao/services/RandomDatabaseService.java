package com.cookiebot.cookiebotbackend.dao.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cookiebot.cookiebotbackend.core.domains.RandomDatabase;
import com.cookiebot.cookiebotbackend.dao.repository.RandomDatabaseRepository;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.BadRequestException;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.ObjectNotFoundException;

@Service
public class RandomDatabaseService {

	@Autowired
	private RandomDatabaseRepository repository;
	
	public RandomDatabase getRandom(){
		List<RandomDatabase> randomList = repository.findAll();

		try {
			RandomDatabase randomArray = randomList.get((int)(randomList.size() * Math.random()));
			return randomArray;
		} catch (Exception e) {
			throw new ObjectNotFoundException("Object Not Found");
		}
	}
	
	public RandomDatabase insert(RandomDatabase randomDatabase) {
		if (repository.findAll().size() >= 1000 ) {
			this.delete();
		}
		
		if (randomDatabase.getId() == null || randomDatabase.getIdMessage() == null) {
			throw new BadRequestException("'id' and 'idMessage' Must Not Be Null");	
		} 
		
		if (repository.findById(randomDatabase.getId()).orElse(null) != null) {
			throw new BadRequestException("ID Already Exists");	
		} 
		
		return repository.insert(randomDatabase);
	}
	
	private void delete() {
		List<RandomDatabase> randomList = repository.findAll();
		repository.deleteById(randomList.get(0).getId());
	}
}
