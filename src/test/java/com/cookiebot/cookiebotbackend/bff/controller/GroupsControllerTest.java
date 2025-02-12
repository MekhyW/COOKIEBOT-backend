package com.cookiebot.cookiebotbackend.bff.controller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cookiebot.cookiebotbackend.bff.service.AuthorizationService;
import com.cookiebot.cookiebotbackend.core.domains.Group;
import com.cookiebot.cookiebotbackend.dao.services.GroupService;
import com.cookiebot.cookiebotbackend.test.BffControllerTest;
import com.cookiebot.cookiebotbackend.utils.BeanUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@BffControllerTest
@WebMvcTest(value = GroupsController.class)
class GroupsControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private GroupService groupService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        BeanUtils.registerBean(context, AuthorizationService.class, new AuthorizationService(this.groupService), "authz");
    }

    @Test
    void testGetMyGroups() throws Exception {
        final var group = Group.builder().groupId("group1").build();

        Jwt jwt = Jwt.withTokenValue("mock-token")
                .header("alg", "RS256")
                .claim("sub", "user-id")
                .build();

        when(groupService.findGroupsUserIsAdmin(jwt.getSubject())).thenReturn(Stream.of(group));

        mockMvc.perform(get("/bff/my-groups")
                        .with(jwt().jwt(jwt)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(group))));

        Mockito.verify(groupService, Mockito.times(1)).findGroupsUserIsAdmin(jwt.getSubject());
        Mockito.verifyNoMoreInteractions(groupService);
    }

}
