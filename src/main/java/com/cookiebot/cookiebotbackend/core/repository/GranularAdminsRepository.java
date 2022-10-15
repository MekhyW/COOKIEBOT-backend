package com.cookiebot.cookiebotbackend.core.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cookiebot.cookiebotbackend.core.domain.GranularAdmins;

public interface GranularAdminsRepository extends MongoRepository<GranularAdmins, String> {

}
