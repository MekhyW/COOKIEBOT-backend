package com.cookiebot.cookiebotbackend.dao.services;

import java.util.*;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.cookiebot.cookiebotbackend.core.domains.Group;
import com.cookiebot.cookiebotbackend.dao.repository.GroupRepository;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.BadRequestException;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.ObjectNotFoundException;

@Service
public class GroupService {

	private final GroupRepository repository;
	private final MongoOperations mongoOperations;

	public GroupService(GroupRepository repository, MongoOperations mongoOperations) {
		this.repository = repository;
		this.mongoOperations = mongoOperations;
	}

	public List<Group> findAll() {
		return repository.findAll();
	}

	public Group findByGroupId(String groupId) {
		return repository.findByGroupId(groupId)
				.orElseThrow(() -> new ObjectNotFoundException("Group not found: " + groupId));
	}

	public Group insert(Group group) {
		try {
			return this.mongoOperations.insert(group);
		} catch (DuplicateKeyException e) {
			throw new BadRequestException("Group already exists: " + group.getGroupId());
		}
	}

	public void delete(String groupId) {
		if (!repository.existsByGroupId(groupId)) {
			throw new ObjectNotFoundException("Admins not found for group: " + groupId);
		}
		repository.deleteByGroupId(groupId);
	}

	public Set<String> findAdmins(String id) {
		Group group = repository.findByGroupId(id).orElseThrow(ObjectNotFoundException::new);
		return group.getAdminUsers();
	}

	public void insertAdmins(String id, Set<String> admins) {
		repository.findByGroupId(id).orElseThrow(
				() -> new ObjectNotFoundException("Not found group with id " + id));

		final Query query = new Query(Criteria.where(Group.GROUP_ID_FIELD).is(id));
		final Update update = new Update().addToSet("adminUsers", admins);

		this.mongoOperations.updateFirst(query, update, Group.class);
	}

	public void deleteAdmins(String id, Set<String> admins) {
		repository.findByGroupId(id).orElseThrow(
				() -> new ObjectNotFoundException("Not found group with id " + id));

		final Query query = new Query(Criteria.where(Group.GROUP_ID_FIELD).is(id));
		final Update update = new Update().pullAll("adminUsers", admins.toArray(new Object[] {}));

		this.mongoOperations.updateFirst(query, update, Group.class);
	}

	public void updateAdmins(String id, Set<String> admins) {
		repository.findByGroupId(id).orElseThrow(
				() -> new ObjectNotFoundException("Not found group with id " + id));

		final Query query = new Query(Criteria.where(Group.GROUP_ID_FIELD).is(id));
		final Update update = new Update().set("adminUsers", admins);

		this.mongoOperations.updateFirst(query, update, Group.class);
	}

	public boolean isAdmin(String userId, String groupId) {
		final Query query = new Query(
				Criteria.where(Group.GROUP_ID_FIELD).is(groupId).and(Group.ADMIN_USERS_FIELD).is(userId));

		var result = this.mongoOperations.count(query, Group.class);

		return result > 0;
	}
}