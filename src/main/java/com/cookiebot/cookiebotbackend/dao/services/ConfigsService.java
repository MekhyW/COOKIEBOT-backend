package com.cookiebot.cookiebotbackend.dao.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cookiebot.cookiebotbackend.core.domain.Configs;
import com.cookiebot.cookiebotbackend.core.repository.ConfigsRepository;
import com.cookiebot.cookiebotbackend.dao.services.exception.BadRequestException;
import com.cookiebot.cookiebotbackend.dao.services.exception.ObjectNotFoundException;

@Service
public class ConfigsService {
	
	@Autowired
	private ConfigsRepository repo;
	
	public List<Configs> findAll(){
		return repo.findAll();
	}
	
	public Configs findById(String id) {
		Configs configs = repo.findById(id).orElseThrow(() -> new ObjectNotFoundException("Object Id not found!"));
		return configs;
	}
	
	public Configs insert(Configs obj) {
		Configs searchId = repo.findById(obj.getId()).orElse(null);
		if (searchId != null) {
			throw new BadRequestException("Id already exists!");
		}
		return repo.insert(obj);
	}
	
	public void delete(String id) {
		findById(id);
		repo.deleteById(id);
	}
	
	public Configs update(Configs obj) {
		Configs newObj = repo.findById(obj.getId()).orElseThrow(() -> new ObjectNotFoundException("Object Id not found!"));
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	private void updateData(Configs newObj, Configs obj) {
		if (obj.getFurbots() != null) {
			newObj.setFurbots(obj.getFurbots());
		}
		
		if (obj.getStickerSpamLimit() != null) {
			newObj.setStickerSpamLimit(obj.getStickerSpamLimit());
		}
		
		if (obj.getTimeWithoutSendingImages() != null) {
			newObj.setTimeWithoutSendingImages(obj.getTimeWithoutSendingImages());
		}
		
		if (obj.getTimeCaptcha() != null) {
			newObj.setTimeCaptcha(obj.getTimeCaptcha());
		}
		
		if (obj.getFunctionsFun() != null) {
			newObj.setFunctionsFun(obj.getFunctionsFun());
		}
		
		if (obj.getFunctionsUtility() != null) {
			newObj.setFunctionsUtility(obj.getFunctionsUtility());
		}
		
		if (obj.getSfw() != null) {
			newObj.setSfw(obj.getSfw());
		}
		
		if (obj.getLanguage() != null) {
			newObj.setLanguage(obj.getLanguage());
		}
	}
}
