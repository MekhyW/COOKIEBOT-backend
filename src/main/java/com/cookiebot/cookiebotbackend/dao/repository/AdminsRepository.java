package com.cookiebot.cookiebotbackend.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.cookiebot.cookiebotbackend.core.domains.Admins;

public interface AdminsRepository extends MongoRepository<Admins, String> {
}
