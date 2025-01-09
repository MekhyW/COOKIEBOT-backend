package com.cookiebot.cookiebotbackend.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.cookiebot.cookiebotbackend.core.domains.Event;
import java.util.Collection;
import java.util.stream.Stream;

public interface EventRepository extends MongoRepository<Event, String> {

    Stream<Event> findAllByGroupIdIn(Collection<String> groupIds);

}
