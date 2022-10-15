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
		Configs configs = repo.findById(id).orElseThrow(() -> new ObjectNotFoundException("ID do objeto não encontrada"));
		return configs;
	}
	
	public Configs insert(Configs obj) {
		Configs searchId = repo.findById(obj.getId()).orElse(null);
		if (searchId != null) {
			throw new BadRequestException("ID Já existe");
		}
		return repo.insert(obj);
	}
	
	public void delete(String id) {
		findById(id);
		repo.deleteById(id);
	}
	
	public Configs update(Configs obj) {
		Configs newObj = repo.findById(obj.getId()).orElseThrow(() -> new ObjectNotFoundException("ID do objeto não encontrada"));
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	private void updateData(Configs newObj, Configs obj) {
		if (obj.getFurbots() != null) {
			newObj.setFurbots(obj.getFurbots());
		}
		
		if (obj.getSticker_spam_limit() != null) {
			newObj.setSticker_spam_limit(obj.getSticker_spam_limit());
		}
		
		if (obj.getTempo_sem_poder_mandar_mensagem() != null) {
			newObj.setTempo_sem_poder_mandar_mensagem(obj.getTempo_sem_poder_mandar_mensagem());
		}
		
		if (obj.getTempo_captcha() != null) {
			newObj.setTempo_captcha(obj.getTempo_captcha());
		}
		
		if (obj.getFuncoes_diversao() != null) {
			newObj.setFuncoes_diversao(obj.getFuncoes_diversao());
		}
		
		if (obj.getFuncoes_utilidade() != null) {
			newObj.setFuncoes_utilidade(obj.getFuncoes_utilidade());
		}
		
		if (obj.getSfw() != null) {
			newObj.setSfw(obj.getSfw());
		}
		
		if (obj.getLanguage() != null) {
			newObj.setLanguage(obj.getLanguage());
		}
	}
}
