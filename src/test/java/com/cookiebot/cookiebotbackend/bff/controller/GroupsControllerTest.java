package com.cookiebot.cookiebotbackend.bff.controller;

import com.cookiebot.cookiebotbackend.bff.service.AuthorizationService;
import com.cookiebot.cookiebotbackend.core.domains.Group;
import com.cookiebot.cookiebotbackend.dao.services.GroupService;
import com.cookiebot.cookiebotbackend.test.BffControllerTest;
import com.cookiebot.cookiebotbackend.utils.BeanUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@BffControllerTest
@WebMvcTest(value = GroupsController.class)
class GroupsControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
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
    public void getMyGroups() throws Exception {
        final var group = new Group();
        group.setGroupId("group1");

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
