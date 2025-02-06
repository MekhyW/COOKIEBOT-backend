package com.cookiebot.cookiebotbackend.dao.repository;

import java.util.List;

import com.cookiebot.cookiebotbackend.core.domains.EventGeo;

public interface EventGeoRepository {
  List<EventGeo> findNear(double lat, double lon, double distance);
}
