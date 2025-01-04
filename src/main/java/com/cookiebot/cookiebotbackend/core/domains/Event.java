package com.cookiebot.cookiebotbackend.core.domains;

import java.text.DecimalFormat;
import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private String name;
    private String description;
    private String coverImageUrl;
    private String city;
    private String state;
    private String country;
    private String locationName;
    private String locationCoordinates; // TODO: Revisar
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer price;
    private Boolean active;
}