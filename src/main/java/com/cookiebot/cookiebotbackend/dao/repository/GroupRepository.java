package com.cookiebot.cookiebotbackend.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.cookiebot.cookiebotbackend.core.domains.Group;

import java.util.Optional;
import java.util.stream.Stream;

public interface GroupRepository extends MongoRepository<Group, String> {

    boolean existsByGroupId(String id);

    Optional<Group> findByGroupId(String id);

    Stream<Group> findAllByAdminUsersContaining(String userId);

    void deleteByGroupId(String id);

}
