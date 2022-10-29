package com.cookiebot.cookiebotbackend.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cookiebot.cookiebotbackend.core.domains.Sticker;

public interface StickerRepository extends MongoRepository<Sticker, String> {

}
