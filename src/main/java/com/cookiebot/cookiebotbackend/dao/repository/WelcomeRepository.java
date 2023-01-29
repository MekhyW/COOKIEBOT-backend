package com.cookiebot.cookiebotbackend.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cookiebot.cookiebotbackend.core.domains.Welcome;

public interface WelcomeRepository extends MongoRepository<Welcome, String> {

}
