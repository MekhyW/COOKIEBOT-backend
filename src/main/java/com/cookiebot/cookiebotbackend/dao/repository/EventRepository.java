package com.cookiebot.cookiebotbackend.dao.repository;

// import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cookiebot.cookiebotbackend.core.domains.Event;

public interface EventRepository extends MongoRepository<Event, String> {

    // List<Event> findByEventname(String eventname);
}
