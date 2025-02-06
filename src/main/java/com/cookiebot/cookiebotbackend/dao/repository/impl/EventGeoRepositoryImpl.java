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

  private MongoOperations mongoOperations;

  public EventGeoRepositoryImpl(MongoOperations mongoOperations) {
    this.mongoOperations = mongoOperations;
  }

  @Override
  public List<EventGeo> findNear(double coordx, double coordy, double distance) {

    GeoNearOperation geoNear = new GeoNearOperation(NearQuery.near(coordx, coordy).maxDistance(distance), "distance");

    Aggregation aggregate = Aggregation.newAggregation(geoNear);

    System.out.println(aggregate.toString());

    // TODO: Define index
    AggregationResults<EventGeo> orderAggregate = mongoOperations.aggregate(aggregate, Event.class, EventGeo.class);

    return orderAggregate.getMappedResults();
  }
}