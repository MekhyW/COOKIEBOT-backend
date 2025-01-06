package com.cookiebot.cookiebotbackend.dao.services;

import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.cookiebot.cookiebotbackend.core.domains.Event;
import com.cookiebot.cookiebotbackend.dao.repository.EventRepository;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.BadRequestException;

@Service
public class EventService {

    private final EventRepository repository;

    public EventService(EventRepository repository) {
        this.repository = repository;
    }

    public List<Event> findAll() {
        return repository.findAll();
    }

    public Event insert(Event event) {
        try {
            return repository.save(event);
        } catch (DuplicateKeyException e) {
            throw new BadRequestException("Event with this ID already exists");
        }
    }
}
