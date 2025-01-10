package com.cookiebot.cookiebotbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity
@SpringBootApplication
public class CookiebotBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CookiebotBackendApplication.class, args);
	}
}