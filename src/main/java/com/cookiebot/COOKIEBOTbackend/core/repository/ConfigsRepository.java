package com.cookiebot.COOKIEBOTbackend.core.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cookiebot.COOKIEBOTbackend.endpoint.domain.configs.Configs;

public interface ConfigsRepository extends MongoRepository<Configs, String> {

}
