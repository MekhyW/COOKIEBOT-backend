package com.cookiebot.cookiebotbackend.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.cookiebot.cookiebotbackend.core.domains.Group;

import java.util.Optional;

public interface GroupRepository extends MongoRepository<Group, String> {

    boolean existsByGroupId(String id);

    Optional<Group> findByGroupId(String id);

    void deleteByGroupId(String id);

}
