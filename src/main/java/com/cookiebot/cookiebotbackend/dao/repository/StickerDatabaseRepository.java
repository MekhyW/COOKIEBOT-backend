package com.cookiebot.cookiebotbackend.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cookiebot.cookiebotbackend.core.domains.StickerDatabase;

public interface StickerDatabaseRepository extends MongoRepository<StickerDatabase, String> {

}
