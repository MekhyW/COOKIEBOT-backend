package com.cookiebot.cookiebotbackend.dao.services;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.cookiebot.cookiebotbackend.core.domains.Admins;
import com.cookiebot.cookiebotbackend.dao.repository.AdminsRepository;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.BadRequestException;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.ObjectNotFoundException;

import java.util.List;

@Service
public class AdminsService {

    private final AdminsRepository repository;
    private final MongoTemplate mongoTemplate;

    public AdminsService(AdminsRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }


    public List<Admins> findAll() {
        return repository.findAll();
    }

    public Admins findByGroupId(String groupId) {
        return repository.findById(groupId)
                .orElseThrow(() -> new ObjectNotFoundException("Admins not found for group: " + groupId));
    }

    public Admins insert(Admins admins) {
       try {
            return repository.save(admins);
        } catch (DuplicateKeyException e) {
            throw new BadRequestException("Admins already exist for group: " + admins.getGroupId());
        }
    }

    public Admins update(String groupId, Admins admins) {
        final var filter = new Query().addCriteria(Criteria.where("id").is(groupId));
        final var update = new Update().set("adminUsers", admins.getAdminUsers());
        final var result = mongoTemplate.updateFirst(filter, update, Admins.class);

        if (result.getMatchedCount() < 1)
           throw new ObjectNotFoundException("Admins not found for group: " + groupId);
        
        return admins;
    }

    public void delete(String groupId) {
        if (!repository.existsById(groupId)) {
            throw new ObjectNotFoundException("Admins not found for group: " + groupId);
        }
        repository.deleteById(groupId);
    }

    public List<String> findGroupsWhereUserIsAdmin(String userId) {
        final var filterUser = Criteria.where("id").is(userId);
        final var query = new Query(
                Criteria.where("adminUsers").elemMatch(filterUser)
        );

        return this.mongoTemplate.find(query, Admins.class).stream().map(Admins::getGroupId).toList();
    }
}