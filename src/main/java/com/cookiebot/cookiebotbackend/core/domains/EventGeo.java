package com.cookiebot.cookiebotbackend.core.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventGeo extends Event {
    private Float distance;
}