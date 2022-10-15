package com.cookiebot.cookiebotbackend.core.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cookiebot.cookiebotbackend.core.domain.Configs;

public interface ConfigsRepository extends MongoRepository<Configs, String> {

}
