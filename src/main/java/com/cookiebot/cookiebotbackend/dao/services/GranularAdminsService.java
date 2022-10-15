package com.cookiebot.cookiebotbackend.dao.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cookiebot.cookiebotbackend.core.domain.GranularAdmins;
import com.cookiebot.cookiebotbackend.core.repository.GranularAdminsRepository;

@Service
public class GranularAdminsService {

	@Autowired
	private GranularAdminsRepository repo;
	
	public List<GranularAdmins> findAll(){
		return repo.findAll();
	}
}
