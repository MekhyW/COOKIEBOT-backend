package com.cookiebot.cookiebotbackend.dao.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cookiebot.cookiebotbackend.core.domains.Sticker;
import com.cookiebot.cookiebotbackend.dao.repository.StickerRepository;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.BadRequestException;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.ObjectNotFoundException;

@Service
public class StickerService {

	private final StickerRepository repository;
	
	@Autowired
	public StickerService(StickerRepository repository) {
		this.repository = repository;
	}
	
	public List<Sticker> findAll(){
		return repository.findAll();
	}
	
	public Sticker findById(String id) {
		return repository.findById(id).orElseThrow(ObjectNotFoundException::new);
	}
	
	public Sticker insert(String id, Sticker sticker) {
		if (repository.findById(id).orElse(null) != null) {
			throw new BadRequestException("ID Already Exists");
		} 
		
		sticker.setId(id);
		return repository.insert(sticker);
	}
	
	public void delete(String id) {
		repository.deleteById(id);
	}
	
	public Sticker update(String id, Sticker sticker) {
		Sticker newSticker = repository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Object Not Found"));
		sticker.setId(id);
		updateSticker(newSticker, sticker);
		return repository.save(newSticker);
	}

	private void updateSticker(Sticker newSticker, Sticker sticker) {
		if (sticker.getLastUsed() != null) {
			newSticker.setLastUsed(sticker.getLastUsed());
		}
	}
}
