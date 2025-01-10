package com.cookiebot.cookiebotbackend.bff.controller;

import com.cookiebot.cookiebotbackend.core.domains.Config;
import com.cookiebot.cookiebotbackend.dao.services.ConfigService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("bff/group")
public class ConfigsController {
    private final ConfigService configService;

    public ConfigsController(ConfigService configService) {
        this.configService = configService;
    }

    @GetMapping("/{groupId}/config")
    @PreAuthorize("@authz.isAdminOfGroup(#root, #groupId)")
    public Config getGroupConfig(@PathVariable String groupId) {
        return this.configService.findById(groupId);
    }

    @PutMapping("/{groupId}/config")
    @PreAuthorize("@authz.isAdminOfGroup(#root, #groupId)")
    public Config updateGroupConfig(@PathVariable String groupId, @RequestBody @Valid Config config) {
        return this.configService.update(groupId, config);
    }
}
