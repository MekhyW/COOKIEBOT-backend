package com.cookiebot.cookiebotbackend.core.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

	@Bean
	public UserDetailsService userDetailsService(SecurityProperties securityProperties) {
		final var user = securityProperties.getUser();

		User.UserBuilder userBuilder = User.withDefaultPasswordEncoder()
				.username(user.getName())
				.password(user.getPassword())
				.roles(user.getRoles().toArray(new String[0]));

		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(userBuilder.build());

		return manager;
	}
	
	@Bean
	@Order(1)
	SecurityFilterChain basicAuthSecurity(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable())
				.securityMatcher("/bff/**", "/v3/api-docs/**", "/swagger-ui/**")
				.authorizeHttpRequests(auth -> auth.requestMatchers("/bff/**").authenticated())
				.authorizeHttpRequests(auth -> auth.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").anonymous())
				.oauth2ResourceServer(o -> o.jwt(Customizer.withDefaults()))
				.build();
	}

	@Bean
	@Order(2)
	SecurityFilterChain jwtAuthSecurity(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth.anyRequest().hasRole("ADMIN"))
				.httpBasic(Customizer.withDefaults())
				.build();
	}
}