package com.cookiebot.cookiebotbackend.dao.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cookiebot.cookiebotbackend.core.domains.Config;
import com.cookiebot.cookiebotbackend.dao.repository.ConfigRepository;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.BadRequestException;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.ObjectNotFoundException;

@Service
public class ConfigService {
	
	private final ConfigRepository repository;
	
	public ConfigService(ConfigRepository repository) {
		this.repository = repository;
	}
	
	public List<Config> findAll() {
		return repository.findAll();
	}
	
	public Config findById(String id) {
		return repository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Object Not Found"));
	}
	
	public Config insert(String id, Config config) {
		if (repository.findById(id).orElse(null) != null) {
			throw new BadRequestException("ID Already Exists");
		}
		
		config.setId(id);
		return repository.insert(config);
	}
	
	public void delete(String id) {
		repository.deleteById(id);
	}
	
	public Config update(String id, Config config) {
		Config newConfig = repository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Object Not Found"));
		config.setId(id);
		updateConfig(newConfig, config);
		return repository.save(newConfig);
	}
	
	private void updateConfig(Config newConfig, Config config) {
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
		
		if (config.getPublisherAsk() != null) {
			newConfig.setPublisherAsk(config.getPublisherAsk());
		}
		
		if (config.getPublisherPost() != null) {
			newConfig.setPublisherPost(config.getPublisherPost());
		}
		
		if (config.getPublisherMembersOnly() != null) {
			newConfig.setPublisherMembersOnly(config.getPublisherMembersOnly());
		}
		
		if (config.getThreadPosts() != null) {
			newConfig.setThreadPosts(config.getThreadPosts());
		}
		
		if (config.getMaxPosts() != null) {
			newConfig.setMaxPosts(config.getMaxPosts());
		}
	}
}
