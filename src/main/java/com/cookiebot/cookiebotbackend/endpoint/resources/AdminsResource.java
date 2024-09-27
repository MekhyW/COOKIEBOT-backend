package com.cookiebot.cookiebotbackend.endpoint.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cookiebot.cookiebotbackend.core.domains.Admins;
import com.cookiebot.cookiebotbackend.dao.services.AdminsService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/admins")
public class AdminsResource {

    private final AdminsService service;

    @Autowired
    public AdminsResource(AdminsService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Admins>> findAll() {
        List<Admins> adminsList = service.findAll();
        return ResponseEntity.ok().body(adminsList);
    }

    @GetMapping(value="/{groupId}")
    public ResponseEntity<Admins> findByGroupId(@PathVariable String groupId) {
        Admins admins = service.findByGroupId(groupId);
        return ResponseEntity.ok().body(admins);
    }

    @PostMapping
    public ResponseEntity<Admins> insert(@RequestBody Admins admins) {
        admins = service.insert(admins);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{groupId}").buildAndExpand(admins.getGroupId()).toUri();
        return ResponseEntity.created(uri).body(admins);
    }

    @PutMapping(value="/{groupId}")
    public ResponseEntity<Admins> update(@RequestBody Admins admins, @PathVariable String groupId) {
        admins = service.update(groupId, admins);
        return ResponseEntity.ok().body(admins);
    }

    @DeleteMapping(value="/{groupId}")
    public ResponseEntity<Void> delete(@PathVariable String groupId) {
        service.delete(groupId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value="/user/{userId}")
    public ResponseEntity<List<String>> findGroupsWhereUserIsAdmin(@PathVariable String userId) {
        List<String> groupIds = service.findGroupsWhereUserIsAdmin(userId);
        return ResponseEntity.ok().body(groupIds);
    }
}