package com.cookiebot.cookiebotbackend.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cookiebot.cookiebotbackend.core.domains.Blacklist;

public interface BlacklistRepository extends MongoRepository<Blacklist, String> {

}
