package com.cookiebot.cookiebotbackend.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import com.cookiebot.cookiebotbackend.core.config.converter.ZonedDateTimeReadConverter;
import com.cookiebot.cookiebotbackend.core.config.converter.ZonedDateTimeWriteConverter;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class MongoConfig {

    @Bean
    public MongoCustomConversions customConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new ZonedDateTimeReadConverter());
        converters.add(new ZonedDateTimeWriteConverter());
        return new MongoCustomConversions(converters);
    }
}