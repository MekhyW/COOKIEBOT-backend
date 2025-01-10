package com.cookiebot.cookiebotbackend.bff.service;

import com.cookiebot.cookiebotbackend.dao.services.GroupService;
import org.slf4j.Logger;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component("authz")
public class AuthorizationService {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AuthorizationService.class);

    private final GroupService groupService;

    public AuthorizationService(GroupService groupService) {
        this.groupService = groupService;
    }

    /**
     * Custom authorization logic to check if the authenticated user is admin of the group.
     *
     * @param operations The MethodSecurityExpressionOperations (contains user, context, etc.)
     * @param groupId The Group ID on which the check will be performed
     * @return true if the user is an admin of the group, false otherwise
     */
    public boolean isAdminOfGroup(MethodSecurityExpressionOperations operations, String groupId) {
        Authentication authentication = operations.getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
            String userId = jwt.getSubject();

            LOGGER.debug("is user {} admin of group {}", userId, groupId);

            return groupService.isAdmin(userId, groupId);
        }

        LOGGER.debug("No authentication found");

        return false;
    }
}
