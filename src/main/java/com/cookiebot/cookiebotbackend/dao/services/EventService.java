package com.cookiebot.cookiebotbackend.dao.services;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import com.cookiebot.cookiebotbackend.core.domains.Group;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.cookiebot.cookiebotbackend.core.domains.Event;
import com.cookiebot.cookiebotbackend.dao.repository.EventRepository;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.BadRequestException;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.ObjectNotFoundException;

@Service
public class EventService {

    private final EventRepository repository;
    private final GroupService groupService;

    public EventService(EventRepository repository, GroupService groupService) {
        this.repository = repository;
        this.groupService = groupService;
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

    public Stream<Event> findByGroupIdIn(Collection<String> groupIds) {
        return repository.findAllByGroupIdIn(groupIds);
    }

    public Stream<Event> findEventsEditable(String userId) {
        final var groups = this.groupService.findGroupsUserIsAdmin(userId).map(Group::getGroupId).toList();
        return this.findByGroupIdIn(groups);
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
