package com.cookiebot.cookiebotbackend.dao.services;

import com.cookiebot.cookiebotbackend.core.domains.StickerDatabase;
import com.cookiebot.cookiebotbackend.dao.repository.StickerDatabaseRepository;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.BadRequestException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataMongoTest
@Testcontainers
public class StickerDatabaseServiceTest {

    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.4.3"));

    @Autowired
    private StickerDatabaseRepository repository;

    @Autowired
    private MongoOperations mongoOperations;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void insertTest() {
        StickerDatabaseService service = new StickerDatabaseService(repository);

        StickerDatabase stickerDatabase = new StickerDatabase();
        stickerDatabase.setId("123");

        StickerDatabase saved = service.insert(stickerDatabase);

        assertEquals(stickerDatabase.getId(), saved.getId());
    }

    @Test
    void insertWithExistingId() {
        StickerDatabaseService service = new StickerDatabaseService(repository);

        StickerDatabase stickerDatabase = new StickerDatabase();
        stickerDatabase.setId("123");

        service.insert(stickerDatabase);

        assertThrows(BadRequestException.class, () -> service.insert(stickerDatabase));
    }

    @AfterEach
    public void cleanup() {
        this.mongoOperations.dropCollection(StickerDatabase.class);
    }
}
