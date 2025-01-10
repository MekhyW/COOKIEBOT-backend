package com.cookiebot.cookiebotbackend.bff.service;

import com.cookiebot.cookiebotbackend.dao.services.GroupService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorizationServiceTest {

    private final GroupService groupService = Mockito.mock(GroupService.class);
    private final AuthorizationService authorizationService = new AuthorizationService(groupService);

    @Test
    void testIsAdminOfGroup_UserIsAdmin_ReturnsTrue() {
        MethodSecurityExpressionOperations operations = mock(MethodSecurityExpressionOperations.class);
        Authentication authentication = mock(Authentication.class);
        Jwt jwt = mock(Jwt.class);

        String groupId = "testGroupId";
        String userId = "testUserId";

        when(operations.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(jwt);
        when(jwt.getSubject()).thenReturn(userId);
        when(groupService.isAdmin(userId, groupId)).thenReturn(true);

        boolean result = authorizationService.isAdminOfGroup(operations, groupId);

        assertTrue(result);
        verify(groupService).isAdmin(userId, groupId);
    }

    @Test
    void testIsAdminOfGroup_UserIsNotAdmin_ReturnsFalse() {
        MethodSecurityExpressionOperations operations = mock(MethodSecurityExpressionOperations.class);
        Authentication authentication = mock(Authentication.class);
        Jwt jwt = mock(Jwt.class);

        String groupId = "testGroupId";
        String userId = "testUserId";

        when(operations.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(jwt);
        when(jwt.getSubject()).thenReturn(userId);
        when(groupService.isAdmin(userId, groupId)).thenReturn(false);

        boolean result = authorizationService.isAdminOfGroup(operations, groupId);

        assertFalse(result);
        verify(groupService).isAdmin(userId, groupId);
    }

    @Test
    void testIsAdminOfGroup_NoAuthentication_ReturnsFalse() {
        MethodSecurityExpressionOperations operations = mock(MethodSecurityExpressionOperations.class);

        when(operations.getAuthentication()).thenReturn(null);

        boolean result = authorizationService.isAdminOfGroup(operations, "testGroupId");

        assertFalse(result);
        verifyNoInteractions(groupService);
    }

    @Test
    void testIsAdminOfGroup_InvalidPrincipalType_ReturnsFalse() {
        MethodSecurityExpressionOperations operations = mock(MethodSecurityExpressionOperations.class);
        Authentication authentication = mock(Authentication.class);

        when(operations.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(new Object()); // Not a Jwt instance

        boolean result = authorizationService.isAdminOfGroup(operations, "testGroupId");

        assertFalse(result);
        verifyNoInteractions(groupService);
    }
}