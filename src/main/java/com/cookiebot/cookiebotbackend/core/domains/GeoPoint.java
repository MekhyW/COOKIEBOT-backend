package com.cookiebot.cookiebotbackend.core.domains;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeoPoint {
    private final String type = "Point";

    @Size(min=2, max=2)
    private Double[] coordinates;
}