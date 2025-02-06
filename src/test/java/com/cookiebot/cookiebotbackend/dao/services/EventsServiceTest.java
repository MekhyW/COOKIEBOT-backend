package com.cookiebot.cookiebotbackend.dao.services;


import com.cookiebot.cookiebotbackend.core.config.MongoConfig;
import com.cookiebot.cookiebotbackend.core.domains.Event;
import com.cookiebot.cookiebotbackend.core.domains.GeoPoint;
import com.cookiebot.cookiebotbackend.dao.repository.EventRepository;
import com.cookiebot.cookiebotbackend.dao.repository.GroupRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;


@DataMongoTest
@Testcontainers
@Import({MongoConfig.class})
class EventsServiceTest {

    @Container
    public static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.6");

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private GroupRepository groupRepository;

    private GroupService groupService;
    private EventService eventService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    public void setup() {
        this.groupService = new GroupService(groupRepository, mongoTemplate);
        this.eventService = new EventService(eventRepository, groupService);
    }

    @AfterEach
    public void cleanup() {
        this.eventRepository.deleteAll();
    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void testFindNear() {
        final var latitude = -73.856077;
        final var longitude = 40.848447;
        final var maxDistance = 1D;

        final var eventNear = Event.builder()
                .name("event").city("canudos")
                .location(createGeoPoint(latitude, longitude, 0.5D, 0D))
                .active(true)
                .build();

        final var eventDistant = Event.builder()
                .name("event").city("narnia")
                .location(createGeoPoint(latitude, longitude, maxDistance * 10, 0D))
                .active(true)
                .build();

        this.eventService.insert(eventNear);
        this.eventService.insert(eventDistant);

        final var actualEvents = this.eventService.findNear(latitude, longitude, maxDistance);
        assertThat(actualEvents).hasSize(1);

        final var actualEvent = actualEvents.get(0);
        assertThat(actualEvent.getDistance()).isLessThanOrEqualTo(maxDistance);
        assertThat(eventNear).usingRecursiveComparison().ignoringFields("distance").isEqualTo(actualEvent);
    }

    /**
     * Creates a GeoPoint a specific distance away from a given base point.
     *
     * @param baseLat      Latitude of the base point.
     * @param baseLon      Longitude of the base point.
     * @param distanceKm   Distance in kilometers from the base point.
     * @param bearingAngle Angle in degrees (0-360) going clockwise, where 0 is north.
     * @return A new GeoPoint at the specified distance and bearing angle.
     */
    private static GeoPoint createGeoPoint(double baseLat, double baseLon, double distanceKm, double bearingAngle) {
        final double EARTH_RADIUS_KM = 6371.01;

        // Convert latitude, longitude, and bearing to radians
        double baseLatRad = Math.toRadians(baseLat);
        double baseLonRad = Math.toRadians(baseLon);
        double bearingRad = Math.toRadians(bearingAngle);

        // Distance in radians
        double angularDistance = distanceKm / EARTH_RADIUS_KM;

        // Haversine formula to calculate new latitude
        double newLatRad = Math.asin(
                Math.sin(baseLatRad) * Math.cos(angularDistance) +
                        Math.cos(baseLatRad) * Math.sin(angularDistance) * Math.cos(bearingRad)
        );

        // Formula to calculate new longitude
        double newLonRad = baseLonRad + Math.atan2(
                Math.sin(bearingRad) * Math.sin(angularDistance) * Math.cos(baseLatRad),
                Math.cos(angularDistance) - Math.sin(baseLatRad) * Math.sin(newLatRad)
        );

        // Convert back to degrees
        double newLat = Math.toDegrees(newLatRad);
        double newLon = Math.toDegrees(newLonRad);

        // Return as GeoPoint
        return new GeoPoint(new Double[]{newLat, newLon});
    }

}
