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
		Configs configs = repo.findById(id).orElseThrow(() -> new ObjectNotFoundException("Object Not Found"));
		return configs;
	}
	
	public Configs insert(Configs configs) {
		Configs searchId = repo.findById(configs.getId()).orElse(null);
		if (searchId == null) {
			return repo.insert(configs);
		} else {
			throw new BadRequestException("ID Already Exists");
		}
	}
	
	public void delete(String id) {
		findById(id);
		repo.deleteById(id);
	}
	
	public Configs update(Configs configs) {
		Configs newObj = repo.findById(configs.getId()).orElseThrow(() -> new ObjectNotFoundException("Object Not Found"));
		updateData(newObj, configs);
		return repo.save(newObj);
	}

	private void updateData(Configs newObj, Configs configs) {
		if (configs.getFurbots() != null) {
			newObj.setFurbots(configs.getFurbots());
		}
		
		if (configs.getStickerSpamLimit() != null) {
			newObj.setStickerSpamLimit(configs.getStickerSpamLimit());
		}
		
		if (configs.getTimeWithoutSendingImages() != null) {
			newObj.setTimeWithoutSendingImages(configs.getTimeWithoutSendingImages());
		}
		
		if (configs.getTimeCaptcha() != null) {
			newObj.setTimeCaptcha(configs.getTimeCaptcha());
		}
		
		if (configs.getFunctionsFun() != null) {
			newObj.setFunctionsFun(configs.getFunctionsFun());
		}
		
		if (configs.getFunctionsUtility() != null) {
			newObj.setFunctionsUtility(configs.getFunctionsUtility());
		}
		
		if (configs.getSfw() != null) {
			newObj.setSfw(configs.getSfw());
		}
		
		if (configs.getLanguage() != null) {
			newObj.setLanguage(configs.getLanguage());
		}
	}
}
