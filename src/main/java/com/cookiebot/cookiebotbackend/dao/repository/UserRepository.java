package com.cookiebot.cookiebotbackend.dao.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.cookiebot.cookiebotbackend.core.domains.User;

public interface UserRepository extends MongoRepository<User, String> {
	  
    List<User> findByUsername(String username);
	List<User> findByBirthdateBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
	
	@Query("{ '$expr': { '$and': [ { '$eq': [ { '$dayOfMonth': '$birthdate' }, ?1 ] }, { '$eq': [ { '$month': '$birthdate' }, ?0 ] } ] } }")
	List<User> findByBirthdateMonthAndDay(int month, int day);
}
