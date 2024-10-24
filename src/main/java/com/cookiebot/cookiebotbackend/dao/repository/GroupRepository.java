package com.cookiebot.cookiebotbackend.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.cookiebot.cookiebotbackend.core.domains.Group;

public interface GroupRepository extends MongoRepository<Group, String> {
}
