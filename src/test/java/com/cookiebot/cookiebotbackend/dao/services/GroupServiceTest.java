package com.cookiebot.cookiebotbackend.dao.services;

import com.cookiebot.cookiebotbackend.core.domains.Group;
import com.cookiebot.cookiebotbackend.dao.repository.GroupRepository;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@Testcontainers
class GroupServiceTest {

    @Container
    public static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.6");

    @Autowired
    private MongoTemplate mongoTemplate;

    private GroupService groupService;

    @Autowired
    private GroupRepository groupRepository;

    @BeforeEach
    public void setup() {
        this.groupService = new GroupService(groupRepository, mongoTemplate);
    }

    @AfterEach
    public void cleanup() {
        this.groupRepository.deleteAll();
    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    public void testFindAll() {
        Group group1 = new Group("group1", Set.of("admin1", "admin2"));
        Group group2 = new Group("group2", Set.of("admin3", "admin4"));

        groupService.insert(group1);
        groupService.insert(group2);

        List<Group> groups = groupService.findAll();

        assertThat(groups).containsExactly(group1, group2);
    }

    @Test
    public void testFindByGroupId_Success() {
        Group group = new Group("group1", Set.of("admin1", "admin2"));
        groupService.insert(group);

        Group foundGroup = groupService.findByGroupId(group.getGroupId());
        assertThat(foundGroup).isEqualTo(group);
    }

    @Test
    public void testFindByGroupId_NotFound() {
        assertThrows(ObjectNotFoundException.class, () -> {
            groupService.findByGroupId("group1");
        });
    }

    @Test
    public void testInsert_GroupAlreadyExists() {
        Group group = new Group("group1", new HashSet<>());
        groupService.insert(group);

        assertThrows(BadRequestException.class, () -> {
            groupService.insert(group);
        });
    }

    @Test
    public void testDelete_Success() {
        Group group = new Group("group1", new HashSet<>());
        groupService.insert(group);

        groupService.delete("group1");
        assertThrows(ObjectNotFoundException.class, () -> {
            groupService.findByGroupId("group1");
        });
    }

    @Test
    public void testDelete_NotFound() {
        assertThrows(ObjectNotFoundException.class, () -> {
            groupService.delete("group1");
        });
    }

    @Test
    public void testFindAdmins() {
        String admin = "admin1";
        Group groupDora = new Group("dora", Set.of(admin, "admin2"));

        groupService.insert(groupDora);

        Set<String> foundAdmins = groupService.findAdmins(groupDora.getGroupId());
        assertThat(foundAdmins).isEqualTo(groupDora.getAdminUsers());
    }

    @Test
    public void testInsertAdmins() {
        Group group = new Group("group1", Set.of("admin2"));
        groupService.insert(group);

        Set<String> newAdmins = Set.of("admin1");
        groupService.insertAdmins(group.getGroupId(), newAdmins);

        Set<String> foundAdmins = groupService.findAdmins(group.getGroupId());
        assertThat(foundAdmins).isEqualTo(Set.of("admin1", "admin2"));
    }

    @Test
    public void testDeleteAdmins() {
        String adminToRemove = "admin1";
        String adminToKeep = "admin2";
        Set<String> admins = Set.of(adminToRemove, adminToKeep);
        Group group = new Group("group1", admins);

        groupService.insert(group);
        groupService.deleteAdmins(group.getGroupId(), Set.of(adminToRemove));

        Set<String> foundAdmins = groupService.findAdmins(group.getGroupId());
        assertThat(foundAdmins).containsExactly(adminToKeep);
    }

    @Test
    public void testUpdateAdmins() {
        Group group = new Group("group1", Set.of("admin3", "admin4"));
        groupService.insert(group);

        Set<String> updatedAdmins = Set.of("admin2");
        groupService.updateAdmins(group.getGroupId(), updatedAdmins);

        Set<String> foundAdmins = groupService.findAdmins(group.getGroupId());
        assertThat(foundAdmins).isEqualTo(updatedAdmins);
    }
}