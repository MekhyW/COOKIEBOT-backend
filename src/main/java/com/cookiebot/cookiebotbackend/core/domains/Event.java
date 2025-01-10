package com.cookiebot.cookiebotbackend.core.domains;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.NumberFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Document(collection = "events")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    public static final String USER_FIELD = "username";

    @Id
    private String id;

    @NotBlank(message = "groupId is required")
    private String groupId;

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "description is required")
    private String description;

    @NotBlank(message = "city is required")
    private String city;

    @NotBlank(message = "state is required")
    private String state;

    @NotBlank(message = "country is required")
    private String country;

    @NotBlank(message = "locationName is required")
    private String locationName;

    private String coverImageUrl;

    @NotNull(message = "startDate is required")
    private ZonedDateTime startDate;

    @NotNull(message = "endDate is required")
    private ZonedDateTime endDate;

    @NumberFormat
    @NotNull(message = "price is required")
    private Integer price;

    private Boolean active = true;
}