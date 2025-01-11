package com.cookiebot.cookiebotbackend.bff.controller;

import com.cookiebot.cookiebotbackend.core.domains.Config;
import com.cookiebot.cookiebotbackend.dao.services.ConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@Tag(name = "BFF bot config", description = "BFF telegram bot configuration API")
@RestController
@RequestMapping("bff/group")
public class ConfigsController {
    private final ConfigService configService;

    public ConfigsController(ConfigService configService) {
        this.configService = configService;
    }

    @Operation(summary = "Get configuration", description = "Get group bot configuration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Config.class))
            }),
            @ApiResponse(responseCode = "404", description = "Group does not exist"),
            @ApiResponse(responseCode = "403", description = "User does not have permission to access bot configuration")
    })
    @GetMapping("/{groupId}/config")
    @PreAuthorize("@authz.isAdminOfGroup(#root, #groupId)")
    public Config getGroupConfig(@PathVariable String groupId) {
        return this.configService.findById(groupId);
    }

    @Operation(summary = "Update configuration", description = "Update bot configuration for group")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Config.class))
            }),
            @ApiResponse(responseCode = "404", description = "Group does not exist"),
            @ApiResponse(responseCode = "403", description = "User does not have permission to update bot configuration")
    })
    @PutMapping("/{groupId}/config")
    @PreAuthorize("@authz.isAdminOfGroup(#root, #groupId)")
    public Config updateGroupConfig(@PathVariable String groupId, @RequestBody @Valid Config config) {
        return this.configService.update(groupId, config);
    }
}
