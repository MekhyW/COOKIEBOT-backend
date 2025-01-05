package com.cookiebot.cookiebotbackend.endpoint.resources;

import java.net.URI;
import java.util.List;
import java.util.Set;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import com.cookiebot.cookiebotbackend.dao.services.GroupService;

@Tag(name = "groups", description = "telegram groups API")
@RestController
@RequestMapping(value = "/groups")
public class GroupResource {

    private final GroupService service;

    public GroupResource(GroupService service) {
        this.service = service;
    }

    @Operation(summary = "Get all groups", description = "Get all registered telegram groups")
    @GetMapping
    public ResponseEntity<List<Group>> findAll() {
        List<Group> adminsList = service.findAll();
        return ResponseEntity.ok().body(adminsList);
    }

    @Operation(summary = "Find a group by id", description = "Find a telegram group by id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Group.class))
            }),
            @ApiResponse(responseCode = "404", description = "Group does not exist")
    })
    @GetMapping(value="/{id}")
    public ResponseEntity<Group> findByGroupId(@PathVariable String id) {
        Group admins = service.findByGroupId(id);
        return ResponseEntity.ok().body(admins);
    }

    @Operation(summary = "Create a new group", description = "Create a new telegram group")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Group.class))
            }),
            @ApiResponse(responseCode = "422", description = "Group with the same ID already exists")
    })
    @PostMapping("/{id}")
    public ResponseEntity<Group> insert(@PathVariable String id, @RequestBody Group group) {
        group.setGroupId(id);
        group = service.insert(group);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(uri).body(group);
    }

    @Operation(summary = "Delete a group", description = "Deletes a telegram group")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Group.class))
            }),
            @ApiResponse(responseCode = "404", description = "Group does not exists")
    })
    @DeleteMapping(value="/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "List admins of a group", description = "List the admin users of a given group ID")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Group.class))
            }),
            @ApiResponse(responseCode = "404", description = "Group does not exists")
    })
	@GetMapping(value = "/{id}/admins")
	public ResponseEntity<Set<String>> findAdmins(@PathVariable String id) {
	    return ResponseEntity.ok().body(service.findAdmins(id));
	}

    @Operation(summary = "Add admins to group", description = "Adds the given admin users to the telegram group keeping the existing ones.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Group.class))
            }),
            @ApiResponse(responseCode = "404", description = "Group does not exists")
    })
	@PostMapping(value="/{id}/admins")
	public ResponseEntity<Void> insertAdmins(@PathVariable String id, @RequestBody Set<String> userIds) {
		service.insertAdmins(id, userIds);
		return ResponseEntity.ok().build();
	}

    @Operation(summary = "Remove group admins", description = "Remove the given admin users to the telegram group keeping the existing ones.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Group.class))
            }),
            @ApiResponse(responseCode = "404", description = "Group does not exists")
    })
	@DeleteMapping(value="/{id}/admins")
	public ResponseEntity<Void> deleteAdmins(@PathVariable String id, Set<String> userIds) {
		service.deleteAdmins(id, userIds);
		return ResponseEntity.ok().build();
	}

    @Operation(summary = "Set group admin", description = "Set the given admin users to the telegram group removing users that are not in the list.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Group.class))
            }),
            @ApiResponse(responseCode = "404", description = "Group does not exists")
    })
	@PutMapping(value="/{id}/admins")
	public ResponseEntity<Void> updateAdmins(@PathVariable String id, @RequestBody Set<String> userIds) {
		service.updateAdmins(id, userIds);
		return ResponseEntity.ok().build();
	}
}
