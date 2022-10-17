package com.cookiebot.cookiebotbackend.dao.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cookiebot.cookiebotbackend.core.domain.Config;
import com.cookiebot.cookiebotbackend.core.repository.ConfigRepository;
import com.cookiebot.cookiebotbackend.dao.services.exception.BadRequestException;
import com.cookiebot.cookiebotbackend.dao.services.exception.ObjectNotFoundException;

@Service
public class ConfigService {
	
	@Autowired
	private ConfigRepository repository;
	
	public List<Config> findAll(){
		return repository.findAll();
	}
	
	public Config findById(String id) {
		
		Config config = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Object Not Found"));
		return config;
	}
	
	public Config insert(Config config) {
		
		if (config.getId() == null) {
			throw new BadRequestException("ID Must Not Be Null");
		}
		
		Config searchId = repository.findById(config.getId()).orElse(null);
		
		if (searchId == null) {
			return repository.insert(config);
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
	
	public Config update(Config config) {
		
		Config newConfig = repository.findById(config.getId()).orElseThrow(() -> new ObjectNotFoundException("Object Not Found"));
		updateData(newConfig, config);
		return repository.save(newConfig);
		
	}

	private void updateData(Config newConfig, Config config) {
		
		if (config.getFurbots() != null) {
			newConfig.setFurbots(config.getFurbots());
		}
		
		if (config.getStickerSpamLimit() != null) {
			newConfig.setStickerSpamLimit(config.getStickerSpamLimit());
		}
		
		if (config.getTimeWithoutSendingImages() != null) {
			newConfig.setTimeWithoutSendingImages(config.getTimeWithoutSendingImages());
		}
		
		if (config.getTimeCaptcha() != null) {
			newConfig.setTimeCaptcha(config.getTimeCaptcha());
		}
		
		if (config.getFunctionsFun() != null) {
			newConfig.setFunctionsFun(config.getFunctionsFun());
		}
		
		if (config.getFunctionsUtility() != null) {
			newConfig.setFunctionsUtility(config.getFunctionsUtility());
		}
		
		if (config.getSfw() != null) {
			newConfig.setSfw(config.getSfw());
		}
		
		if (config.getLanguage() != null) {
			newConfig.setLanguage(config.getLanguage());
		}
	}
}
