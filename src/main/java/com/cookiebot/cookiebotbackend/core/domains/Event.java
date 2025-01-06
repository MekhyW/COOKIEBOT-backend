package com.cookiebot.cookiebotbackend.core.domains;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.NumberFormat;

import com.cookiebot.cookiebotbackend.core.validators.ValidDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "events")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    public static final String USER_FIELD = "username";

    @Id
    private String id;

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
    @ValidDate(message = "startDate must be in yyyy/mm/dd format and be valid")
    private String startDate;

    @NotNull(message = "endDate is required")
    @ValidDate(message = "endDate must be in yyyy/mm/dd format and be valid")
    private String endDate;

    @NumberFormat
    @NotNull(message = "price is required")
    private Integer price;

    private Boolean active = true;
}