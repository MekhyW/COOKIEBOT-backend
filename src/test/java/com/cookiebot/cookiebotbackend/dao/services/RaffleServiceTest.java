package com.cookiebot.cookiebotbackend.dao.services;

import com.cookiebot.cookiebotbackend.core.domains.Raffle;
import com.cookiebot.cookiebotbackend.core.domains.RaffleParticipant;
import com.cookiebot.cookiebotbackend.dao.repository.RaffleRepository;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.BadRequestException;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@Testcontainers
class RaffleServiceTest {

    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.4.3"));

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RaffleRepository repository;

    private RaffleService raffleService;

    @BeforeEach
    public void setup() {
        this.raffleService = new RaffleService(repository, mongoTemplate);
    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void testInsertParticipant() {
        Raffle raffle = new Raffle();
        raffle.setName("123");

        RaffleParticipant participant = new RaffleParticipant();
        participant.setUser("a");

        raffleService.insert(raffle);
        raffleService.insertParticipant(raffle.getName(), participant);

        Raffle updatedRaffle = repository.findById("123").get();

        assertEquals(1, updatedRaffle.getParticipants().size());
        assertEquals(participant, updatedRaffle.getParticipants().get(0));
    }

    @Test
    void testInsertParticipantNoUserShouldThrowException() {
        Raffle raffle = new Raffle();
        raffle.setName("123");

        RaffleParticipant participant = new RaffleParticipant();
        participant.setUser(null);

        assertThrows(BadRequestException.class, () -> raffleService.insertParticipant(raffle.getName(), participant));
    }

    @Test
    void testInsertParticipantRaffleDoesNotExistShouldThrowException() {
        RaffleParticipant participant = new RaffleParticipant();
        participant.setUser("user1");

        assertThrows(ObjectNotFoundException.class, () -> raffleService.insertParticipant("nonexistentId", participant));
    }

    @Test
    void testInsertParticipantAlreadyInRaffleShouldNotAddParticipant() {
        Raffle raffle = new Raffle();
        raffle.setName("123");

        raffleService.insert(raffle);

        RaffleParticipant participant = new RaffleParticipant();
        participant.setUser("user1");

        // Add the participant once
        raffleService.insertParticipant(raffle.getName(), participant);

        // Attempt to add the participant a second time
        raffleService.insertParticipant(raffle.getName(), participant);

        Raffle updatedRaffle = repository.findById(raffle.getName()).get();

        // Despite two attempts to add, there should only be one participant in the raffle
        assertEquals(1, updatedRaffle.getParticipants().size());
        assertEquals(participant, updatedRaffle.getParticipants().get(0));
    }

    @AfterEach
    public void cleanup() {
        this.mongoTemplate.dropCollection(Raffle.class);
    }

}
