package com.cookiebot.cookiebotbackend.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cookiebot.cookiebotbackend.core.domains.RandomDatabase;

public interface RandomDatabaseRepository extends MongoRepository<RandomDatabase, String> {

}
