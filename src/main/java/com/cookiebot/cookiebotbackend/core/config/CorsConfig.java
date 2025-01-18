package com.cookiebot.cookiebotbackend.core.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.util.List;


@Configuration
@ConfigurationProperties(prefix = "spring.security.cors")
@NoArgsConstructor
@Data
public class CorsConfig {
    private List<String> allowedOrigins;
    private List<String> allowedMethods;
    private List<String> allowedHeaders;
    private List<String> exposedHeaders;
    private boolean allowCredentials;
    private String pathPattern;
}
