package com.cookiebot.cookiebotbackend.bff.controller;

import com.cookiebot.cookiebotbackend.core.domains.Group;
import com.cookiebot.cookiebotbackend.dao.services.GroupService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
@RequestMapping("bff")
public class GroupsController {

    private final GroupService groupService;

    public GroupsController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/my-groups")
    public Stream<Group> getMyGroups(@AuthenticationPrincipal Jwt jwt) {
        return this.groupService.findGroupsUserIsAdmin(jwt.getSubject());
    }

}
