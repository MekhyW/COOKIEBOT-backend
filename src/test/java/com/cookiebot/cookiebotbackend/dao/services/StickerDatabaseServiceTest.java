package com.cookiebot.cookiebotbackend.dao.services;

import com.cookiebot.cookiebotbackend.core.domains.StickerDatabase;
import com.cookiebot.cookiebotbackend.dao.DataGenerator;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        StickerDatabaseService service = new StickerDatabaseService(repository, mongoOperations);

        StickerDatabase stickerDatabase = new StickerDatabase();
        stickerDatabase.setId("123");

        StickerDatabase saved = service.insert(stickerDatabase);

        assertEquals(stickerDatabase.getId(), saved.getId());
    }

    @Test
    void insertWithExistingId() {
        StickerDatabaseService service = new StickerDatabaseService(repository, mongoOperations);

        StickerDatabase stickerDatabase = new StickerDatabase();
        stickerDatabase.setId("123");

        service.insert(stickerDatabase);

        assertThrows(BadRequestException.class, () -> service.insert(stickerDatabase));
    }

    @Test
    void randomStickerOneSticker() {
        final var service = new StickerDatabaseService(repository, mongoOperations);
        final var sticker = DataGenerator.generateSticker();
        service.insert(sticker);

        final var actual = service.getRandom();

        assertEquals(sticker.getId(), actual.getId());
    }

    /**
     * This test case validates the logic of the {@link StickerDatabaseService#getRandom()} method.
     * The test generates and inserts 50 random StickerDatabase objects into the repository.
     * It then performs 50 testing iterations and each iteration continues to fetch
     * random StickerDatabase objects from the repository until a duplicate (previously fetched)
     * StickerDatabase object is encountered.
     * <p>
     * The retrieval logic in each iteration represents a scenario of repetitive database
     * access with the intention of getting unique StickerDatabase items each time.
     * The HashSet data structure is used to ensure the uniqueness of fetched items in each iteration.
     * <p>
     * After the iterations, the test calculates an average of the unique
     * StickerDatabase objects fetched in each iteration.
     * The test asserts that this average number must be greater than 3.
     * <p>
     * Thus, the test verifies that {@link StickerDatabaseService#getRandom()} can produce a
     * reasonable ratio of unique StickerDatabase objects under the given testing scenario.
     */
    @Test
    void randomStickerMultipleStickers() {
        final var service = new StickerDatabaseService(repository, mongoOperations);
        final var stickers = IntStream.range(0, 50)
                .mapToObj(__ -> DataGenerator.generateSticker())
                .toList();

        stickers.forEach(service::insert);

        final var testIterations = 50;
        final var testResult = new ArrayList<HashSet<StickerDatabase>>(testIterations);

        for (int i = 0; i < testIterations; i++) {
            final var previousStickers = new HashSet<StickerDatabase>();

            for (;;) {
                final var actual = service.getRandom();

                if (previousStickers.contains(actual)) {
                    break;
                }

                previousStickers.add(actual);
            }

            testResult.add(previousStickers);
        }

        final var averageDifferentStickers = testResult.stream().mapToInt(HashSet::size).average().orElseThrow();

        assertTrue(averageDifferentStickers > 3);
    }

    @Test
    void randomStickerEmpty() {
        final var service = new StickerDatabaseService(repository, mongoOperations);

        final var actual = service.getRandom();

        assertNull(actual);
    }

    @AfterEach
    public void cleanup() {
        this.mongoOperations.dropCollection(StickerDatabase.class);
    }
}
