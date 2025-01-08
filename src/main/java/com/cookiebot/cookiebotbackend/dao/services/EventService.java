package com.cookiebot.cookiebotbackend.dao.services;

import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.cookiebot.cookiebotbackend.core.domains.Event;
import com.cookiebot.cookiebotbackend.dao.repository.EventRepository;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.BadRequestException;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.ObjectNotFoundException;

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

    public Event findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Event not found"));
    }

    public void delete(String id) {
        if (!repository.existsById(id)) {
            throw new ObjectNotFoundException("Event not found");
        }

        repository.deleteById(id);
    }

    public Event update(String id, Event event) {
        if (!repository.existsById(id)) {
            throw new ObjectNotFoundException("Event not found");
        }

        return repository.save(event);
    }
}
