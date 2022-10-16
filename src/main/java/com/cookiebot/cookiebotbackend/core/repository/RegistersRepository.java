package com.cookiebot.cookiebotbackend.core.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cookiebot.cookiebotbackend.core.domain.Registers;

public interface RegistersRepository extends MongoRepository<Registers, String> {

}
