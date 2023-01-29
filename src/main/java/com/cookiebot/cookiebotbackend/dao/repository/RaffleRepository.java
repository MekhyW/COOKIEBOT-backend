package com.cookiebot.cookiebotbackend.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cookiebot.cookiebotbackend.core.domains.Raffle;

public interface RaffleRepository extends MongoRepository<Raffle, String> {

}
