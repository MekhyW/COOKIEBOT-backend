package com.cookiebot.cookiebotbackend.dao.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.cookiebot.cookiebotbackend.core.domains.Group;
import com.cookiebot.cookiebotbackend.core.domains.User;
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

    public Group findByGroupId(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Group not found: " + id));
    }

    public Group insert(Group group) {
       try {
            return repository.save(group);
        } catch (DuplicateKeyException e) {
            throw new BadRequestException("Group already exists: " + group.getGroupId());
        }
    }

    public void delete(String groupId) {
        if (!repository.existsById(groupId)) {
            throw new ObjectNotFoundException("Admins not found for group: " + groupId);
        }
        repository.deleteById(groupId);
    }
    
    
    // Manage Admins from 
	public List<User> findAdmins(String id) {
		Group group = repository.findById(id).orElseThrow(ObjectNotFoundException::new);
		return group.getAdminUsers();
	}
	
	public void insertAdmins(String id, User admin) {
		if (admin.getUsername() == null) {
			throw new BadRequestException("Username Must Not Be Null");
		}
		
		repository.findById(id).orElseThrow(
				() -> new ObjectNotFoundException("Not found raffled with id " + id));
		
		final Query query = new Query(Criteria.where("_id").is(id));
		final Update update = new Update().addToSet("adminUsers", admin);
		
		this.mongoOperations.updateFirst(query, update, Group.class);
	}
	
	public void deleteAdmins(String id, User admin) {
		if (admin.getUsername() == null) {
			throw new BadRequestException("Username Must Not Be Null");
		}
		
		repository.findById(id).orElseThrow(
				() -> new ObjectNotFoundException("Not found Username with id " + id));
		
		final Criteria filterUser = Criteria.where(User.USER_FIELD)
				.is(admin.getUsername());
		
		final Query query = new Query(
				Criteria.where("_id").is(id).and("admin").elemMatch(filterUser));
		
		final Update update = new Update().pull("admin",
				Map.of(User.USER_FIELD, admin.getUsername()));
		
		this.mongoOperations.updateFirst(query, update, Group.class);
	}
	
	public void updateAdmins(String id, User admin) {
		if (admin.getUsername() == null) {
			throw new BadRequestException("Username Must Not Be Null");
		}
		
		Group group = repository.findById(id).orElseThrow(ObjectNotFoundException::new);
		List<User> adminList = new ArrayList<>(group.getAdminUsers());
		Integer adminSize = adminList.size();
		
		boolean foundAdmin = false;
		for (int adminArray = adminSize
				- 1; adminArray >= 0; adminArray--) {
			if (adminList.get(adminArray).getUsername().matches(admin.getUsername())) {
				foundAdmin = true;
				adminList.remove(adminArray);
				adminList.addAll(Arrays.asList(admin));
				group.setAdminUsers(adminList);
				repository.save(group);
			}
		}
		
		if (!foundAdmin) {
			throw new ObjectNotFoundException("Username Not Found");
		}
	}
}