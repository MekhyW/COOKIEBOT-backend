package com.cookiebot.cookiebotbackend.core.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cookiebot.cookiebotbackend.core.domain.Config;

public interface ConfigRepository extends MongoRepository<Config, String> {

}
