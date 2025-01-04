package com.cookiebot.cookiebotbackend.endpoint.resources;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cookiebot.cookiebotbackend.core.domains.Event;
import com.cookiebot.cookiebotbackend.dao.services.EventService;

@RestController
@RequestMapping(value = "/events")
public class EventResource {

    private final EventService service;

    public EventResource(EventService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Event>> findAll() {
        List<Event> events = service.findAll();
        return ResponseEntity.ok().body(events);

    }

    @PostMapping
    public ResponseEntity<Event> insert(@RequestBody Event event) {
        event = service.insert(event);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(event.getId()).toUri();
        return ResponseEntity.created(uri).body(event);
    }
}
