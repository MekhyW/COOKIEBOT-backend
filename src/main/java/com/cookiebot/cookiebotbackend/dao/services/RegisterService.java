package com.cookiebot.cookiebotbackend.dao.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.cookiebot.cookiebotbackend.core.domains.Register;
import com.cookiebot.cookiebotbackend.core.domains.UserRegister;
import com.cookiebot.cookiebotbackend.dao.repository.RegisterRepository;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.BadRequestException;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.ObjectNotFoundException;

@Service
public class RegisterService {
	
	private final RegisterRepository repository;
	private final MongoOperations mongoOperations;
	
	public RegisterService(RegisterRepository repository, MongoOperations mongoOperations) {
		this.repository = repository;
		this.mongoOperations = mongoOperations;
	}
	
	public List<Register> findAll() {
		return repository.findAll();
	}
	
	public Register findById(String id) {
		return repository.findById(id).orElseThrow(ObjectNotFoundException::new);
	}
	
	public Register insert(String id, Register register) {
		if (repository.findById(id).orElse(null) != null) {
			throw new BadRequestException("ID Already Exists");
		}
		
		register.setId(id);
		return repository.insert(register);
	}
	
	public void delete(String id) {
		repository.deleteById(id);
	}
	
	public List<UserRegister> findUsers(String id) {
		Register register = repository.findById(id).orElseThrow(ObjectNotFoundException::new);
		
		return register.getUsers();
	}
	
	
	public void insertUser(String id, UserRegister user) {
		if (user.getUser() == null) {
			throw new BadRequestException("User Must Not Be Null");
		}
		
		repository.findById(id).orElseThrow(ObjectNotFoundException::new);
		
		Query queryCheck = new Query(
		    Criteria.where("_id").is(id)
		            .and("users").elemMatch(Criteria.where(UserRegister.USER_FIELD).is(user.getUser()))
		);
		    
		boolean exists = mongoOperations.exists(queryCheck, Register.class);
		
		if (exists) {
		    throw new BadRequestException("User already exists");
		}
		
		final Query query = new Query(Criteria.where("_id").is(id));
		final Update update = new Update().addToSet("users", user);
		
		this.mongoOperations.updateFirst(query, update, Register.class);
	}
	
	
	public void deleteUser(String id, UserRegister user) {
		if (user.getUser() == null) {
			throw new BadRequestException("User Must Not Be Null");
		}
		
		repository.findById(id).orElseThrow(ObjectNotFoundException::new);
		
		final Criteria filterUser = Criteria.where(UserRegister.USER_FIELD)
				.is(user.getUser());
		
		final Query query = new Query(
				Criteria.where("_id").is(id).and("users").elemMatch(filterUser));
		
		final Update update = new Update().pull("users",
				Map.of(UserRegister.USER_FIELD, user.getUser()));
		
		this.mongoOperations.updateFirst(query, update, Register.class);
	}
	
	
	public void updateUser(String id, UserRegister user) {
		if (user.getUser() == null) {
			throw new BadRequestException("User Must Not Be Null");
		}
		
		repository.findById(id).orElseThrow(ObjectNotFoundException::new);
		
		Query queryCheck = new Query(
				Criteria.where("_id").is(id)
			    .and("users").elemMatch(Criteria.where(UserRegister.USER_FIELD).is(user.getUser()))
		);
			    
		boolean exists = mongoOperations.exists(queryCheck, Register.class);
			
		if (exists) {
			final Criteria filterUser = Criteria.where(UserRegister.USER_FIELD)
					.is(user.getUser());
			
			final Query query = new Query(
					Criteria.where("_id").is(id).and("users").elemMatch(filterUser));
			
			final Update update = new Update().set("users.$", user);
			
			this.mongoOperations.updateFirst(query, update, Register.class);
			
		} else {
			this.insertUser(id, user);
		}
	}
}