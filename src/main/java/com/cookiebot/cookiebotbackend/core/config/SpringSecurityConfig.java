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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

	@Bean
	UserDetailsService userDetailsService(SecurityProperties securityProperties) {
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
	UrlBasedCorsConfigurationSource corsConfiguration(final CorsConfig config) {
		final var cors = new CorsConfiguration()
				.setAllowedOriginPatterns(config.getAllowedOrigins());
		cors.setAllowedHeaders(config.getAllowedHeaders());
		cors.setAllowedMethods(config.getAllowedMethods());
		cors.setExposedHeaders(config.getExposedHeaders());
		cors.setAllowCredentials(config.isAllowCredentials());

		final var source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration(config.getPathPattern(), cors);

		return source;
	}
	
	@Bean
	@Order(1)
	SecurityFilterChain basicAuthSecurity(HttpSecurity http, UrlBasedCorsConfigurationSource cors) throws Exception {
		return http.csrf(csrf -> csrf.disable())
				.securityMatcher("/bff/**", "/v3/api-docs/**", "/swagger-ui/**", "/actuator/health", "/actuator/prometheus")
				.authorizeHttpRequests(auth -> auth.requestMatchers("/bff/**").authenticated())
				.cors( c -> c.configurationSource(cors))
				.authorizeHttpRequests(auth ->
						auth.requestMatchers(
								"/swagger-ui/**", "/v3/api-docs/**",
								"/actuator/health", "/actuator/prometheus"
						).anonymous()
				)
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