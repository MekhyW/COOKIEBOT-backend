package com.cookiebot.cookiebotbackend.core.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.info.GitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class SwaggerConfiguration {
    private static final String GIT_BUILD_VERSION = "build.version";

    @Bean
    public OpenAPI springShopOpenAPI(final Optional<GitProperties> gitProperties) {
        final var version = gitProperties.map(p -> p.get(GIT_BUILD_VERSION)).orElse("develop");

        return new OpenAPI()
                .info(new Info().title("Cookiebot backend API")
                        .description("Cookiebot backend services")
                        .version(version)
                        .license(new License().name("Apache 2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("Cookiebot backend README")
                        .url("https://github.com/MekhyW/COOKIEBOT-backend"));
    }

}
