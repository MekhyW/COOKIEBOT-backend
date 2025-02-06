package com.cookiebot.cookiebotbackend.endpoint.resources;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cookiebot.cookiebotbackend.core.domains.Event;
import com.cookiebot.cookiebotbackend.core.domains.EventGeo;
import com.cookiebot.cookiebotbackend.dao.services.EventService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/events")
public class EventResource {

    private final EventService service;

    public EventResource(EventService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Event> insert(@RequestBody @Valid Event event) {
        event = service.insert(event);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(event.getId()).toUri();
        return ResponseEntity.created(uri).body(event);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Event> update(@RequestBody @Valid Event event, @PathVariable String id) {
        event = service.update(id, event);
        return ResponseEntity.ok().body(event);
    }

    @GetMapping
    public ResponseEntity<List<Event>> findAll() {
        List<Event> events = service.findAll();
        return ResponseEntity.ok().body(events);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Event> findById(@PathVariable String id) {
        Event event = service.findById(id);
        return ResponseEntity.ok().body(event);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/near/{xcoord}/{ycoord}/{distance}")
    public ResponseEntity<List<EventGeo>> findNear(@PathVariable Double xcoord, @PathVariable Double ycoord,
            @PathVariable Double distance) {
        List<EventGeo> events = service.findNear(xcoord, ycoord, distance);
        return ResponseEntity.ok().body(events);
    }
}
