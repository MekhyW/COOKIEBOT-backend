package com.cookiebot.cookiebotbackend.dao.repository.impl;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GeoNearOperation;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.stereotype.Component;

import com.cookiebot.cookiebotbackend.core.domains.Event;
import com.cookiebot.cookiebotbackend.core.domains.EventGeo;
import com.cookiebot.cookiebotbackend.dao.repository.EventGeoRepository;

@Component
@Primary
public class EventGeoRepositoryImpl implements EventGeoRepository {

  private final MongoOperations mongoOperations;

  public EventGeoRepositoryImpl(MongoOperations mongoOperations) {
    this.mongoOperations = mongoOperations;
  }

  @Override
  public List<EventGeo> findNear(double lat, double lon, double distance) {
    NearQuery nearQuery = NearQuery.near(lat, lon).inKilometers().maxDistance(distance);
    GeoNearOperation geoNear = new GeoNearOperation(nearQuery, EventGeo.DISTANCE_FIELD)
            .useIndex(Event.LOCATION_FIELD);

    Aggregation aggregate = Aggregation.newAggregation(geoNear);

    AggregationResults<EventGeo> orderAggregate = mongoOperations.aggregate(aggregate, Event.class, EventGeo.class);

    return orderAggregate.getMappedResults();
  }
}