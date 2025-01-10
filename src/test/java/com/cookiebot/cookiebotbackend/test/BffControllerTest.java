package com.cookiebot.cookiebotbackend.test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@EnableMethodSecurity
public @interface BffControllerTest {

}
