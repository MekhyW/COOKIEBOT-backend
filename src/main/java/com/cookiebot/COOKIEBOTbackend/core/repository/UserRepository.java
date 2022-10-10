package com.cookiebot.COOKIEBOTbackend.core.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cookiebot.COOKIEBOTbackend.endpoint.domain.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

}
