package com.cookiebot.cookiebotbackend.bff.controller;

import com.cookiebot.cookiebotbackend.bff.service.AuthorizationService;
import com.cookiebot.cookiebotbackend.core.domains.Config;
import com.cookiebot.cookiebotbackend.dao.services.ConfigService;
import com.cookiebot.cookiebotbackend.dao.services.GroupService;
import com.cookiebot.cookiebotbackend.test.BffControllerTest;
import com.cookiebot.cookiebotbackend.utils.BeanUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@BffControllerTest
@WebMvcTest(value = ConfigsController.class)
class ConfigsControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GroupService groupService;

    @MockBean
    private ConfigService configService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        BeanUtils.registerBean(context, AuthorizationService.class, new AuthorizationService(this.groupService), "authz");
    }

    @Test
    void testGetGroupConfig_userIsAdmin() throws Exception {
        final var config = new Config();
        config.setId("group1");
        config.setSfw(true);
        config.setLanguage("pt-BR");

        Jwt jwt = Jwt.withTokenValue("mock-token")
                .header("alg", "RS256")
                .claim("sub", "user-id")
                .build();

        when(groupService.isAdmin(jwt.getSubject(), config.getId())).thenReturn(true);
        when(configService.findById(config.getId())).thenReturn(config);

        mockMvc.perform(get("/bff/group/{groupId}/config", config.getId())
                        .with(jwt().jwt(jwt)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(config)));

        Mockito.verify(groupService, Mockito.times(1)).isAdmin(jwt.getSubject(), config.getId());
        Mockito.verify(configService, Mockito.times(1)).findById(config.getId());
        Mockito.verifyNoMoreInteractions(groupService, configService);
    }

    @Test
    void testGetGroupConfig_userNotAdmin() throws Exception {
        final var config = new Config();
        config.setId("group1");
        config.setSfw(true);
        config.setLanguage("pt-BR");

        Jwt jwt = Jwt.withTokenValue("mock-token")
                .header("alg", "RS256")
                .claim("sub", "user-id")
                .build();

        when(groupService.isAdmin(jwt.getSubject(), config.getId())).thenReturn(false);

        mockMvc.perform(get("/bff/group/{groupId}/config", config.getId())
                        .with(jwt().jwt(jwt)))
                .andExpect(status().isForbidden())
                .andExpect(content().string(""));

        Mockito.verify(groupService, Mockito.times(1)).isAdmin(jwt.getSubject(), config.getId());
        Mockito.verifyNoMoreInteractions(groupService, configService);
    }

    @Test
    void testUpdateGroupConfig_userIsAdmin() throws Exception {
        final var config = new Config();
        config.setId("group1");
        config.setSfw(true);
        config.setLanguage("pt-BR");

        Jwt jwt = Jwt.withTokenValue("mock-token")
                .header("alg", "RS256")
                .claim("sub", "user-id")
                .build();

        when(groupService.isAdmin(jwt.getSubject(), config.getId())).thenReturn(true);
        when(configService.update(Mockito.eq(config.getId()), Mockito.any())).thenReturn(config);

        mockMvc.perform(put("/bff/group/{groupId}/config", config.getId())
                        .with(jwt().jwt(jwt))
                        .content(objectMapper.writeValueAsString(config))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(config)));

        var configCaptor = ArgumentCaptor.forClass(Config.class);

        Mockito.verify(groupService, Mockito.times(1)).isAdmin(jwt.getSubject(), config.getId());
        Mockito.verify(configService, Mockito.times(1)).update(Mockito.eq(config.getId()), configCaptor.capture());
        Mockito.verifyNoMoreInteractions(groupService, configService);

        var actualConfig = configCaptor.getValue();

        assertThat(actualConfig, Is.is(config));
    }

    @Test
    void testUpdateGroupConfig_userIsNotAdmin() throws Exception {
        final var config = new Config();
        config.setId("group1");
        config.setSfw(true);
        config.setLanguage("pt-BR");

        Jwt jwt = Jwt.withTokenValue("mock-token")
                .header("alg", "RS256")
                .claim("sub", "user-id")
                .build();

        when(groupService.isAdmin(jwt.getSubject(), config.getId())).thenReturn(false);

        mockMvc.perform(put("/bff/group/{groupId}/config", config.getId())
                        .with(jwt().jwt(jwt))
                        .content(objectMapper.writeValueAsString(config))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(content().string(""));

        Mockito.verify(groupService, Mockito.times(1)).isAdmin(jwt.getSubject(), config.getId());
        Mockito.verifyNoMoreInteractions(groupService, configService);
    }
}
