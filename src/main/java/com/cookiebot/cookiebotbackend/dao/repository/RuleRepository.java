package com.cookiebot.cookiebotbackend.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cookiebot.cookiebotbackend.core.domain.Rule;

public interface RuleRepository extends MongoRepository<Rule, String>{

}
