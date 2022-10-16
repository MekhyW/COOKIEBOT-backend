package com.cookiebot.cookiebotbackend.core.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cookiebot.cookiebotbackend.core.domain.Register;

public interface RegisterRepository extends MongoRepository<Register, String> {

}
