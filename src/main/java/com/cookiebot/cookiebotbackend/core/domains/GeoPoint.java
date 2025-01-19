package com.cookiebot.cookiebotbackend.core.domains;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeoPoint {
    // location: { type: "Point", coordinates: [ -73.97, 40.77 ] },
    private static final String TYPE = "Point";

    @Size(min=2, max=2)
    private Double[] coordinates;

    @JsonProperty
    public String getType() {
        return TYPE;
    }
}