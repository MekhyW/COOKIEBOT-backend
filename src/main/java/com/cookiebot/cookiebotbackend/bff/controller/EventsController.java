package com.cookiebot.cookiebotbackend.bff.controller;

import com.cookiebot.cookiebotbackend.core.domains.Event;
import com.cookiebot.cookiebotbackend.dao.services.EventService;
import org.slf4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@RestController
@RequestMapping("bff/events")
public class EventsController {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(EventsController.class);

    private final EventService eventService;

    public EventsController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("")
    public Stream<Event> getEvents(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();

        return this.eventService.findEventsEditable(userId);
    }

    @PostMapping("")
    @PreAuthorize("@authz.isAdminOfGroup(#root, #event.groupId)")
    public Event createEvent(@RequestBody Event event) {
        event.setId(null);

        return this.eventService.insert(event);
    }

}
