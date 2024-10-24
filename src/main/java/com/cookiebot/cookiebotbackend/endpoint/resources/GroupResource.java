package com.cookiebot.cookiebotbackend.endpoint.resources;

import java.net.URI;
import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cookiebot.cookiebotbackend.core.domains.Group;
import com.cookiebot.cookiebotbackend.core.domains.User;
import com.cookiebot.cookiebotbackend.dao.services.GroupService;

@RestController
@RequestMapping(value = "/groups")
public class GroupResource {

    private final GroupService service;

    public GroupResource(GroupService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Group>> findAll() {
        List<Group> adminsList = service.findAll();
        return ResponseEntity.ok().body(adminsList);
    }

    @GetMapping(value="/{groupId}")
    public ResponseEntity<Group> findByGroupId(@PathVariable String id) {
        Group admins = service.findByGroupId(id);
        return ResponseEntity.ok().body(admins);
    }

    @PostMapping("/{groupId}")
    public ResponseEntity<Group> insert(@PathVariable String id, @RequestBody Group group) {
        group.setGroupId(id);
        group = service.insert(group);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(uri).body(group);
    }

    @DeleteMapping(value="/{groupId}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
	// Manage Admins from Group
	@GetMapping(value = "/{id}/admins")
	public ResponseEntity<List<User>> findAdmins(@PathVariable String id) {
	List<User> userList = service.findAdmins(id);
	return ResponseEntity.ok().body(userList);
	}
	
	@PostMapping(value="/{id}/admins")
	public ResponseEntity<Void> insertAdmins(@PathVariable String id, @RequestBody Set<String> userIds) {
		service.insertAdmins(id, userIds);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping(value="/{id}/admins")
	public ResponseEntity<Void> deleteAdmins(@PathVariable String id, Set<String> userIds) {
		service.deleteAdmins(id, userIds);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping(value="/{id}/admins")
	public ResponseEntity<Void> updateAdmins(@PathVariable String id, @RequestBody Set<String> userIds) {
		service.updateAdmins(id, userIds);
		return ResponseEntity.ok().build();
	}
}
