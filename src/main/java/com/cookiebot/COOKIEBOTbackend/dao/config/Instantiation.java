package com.cookiebot.COOKIEBOTbackend.dao.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.cookiebot.COOKIEBOTbackend.core.repository.UserRepository;
import com.cookiebot.COOKIEBOTbackend.endpoint.domain.User;

@Configuration
public class Instantiation implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		userRepository.deleteAll();
		
		User myghi = new User(null, "Myghi63");
		User mekhy = new User(null, "MekhyW");
		User zippo = new User(null, "Zippo_Gamer");
		
		userRepository.saveAll(Arrays.asList(myghi, mekhy, zippo));
	}
	
}
