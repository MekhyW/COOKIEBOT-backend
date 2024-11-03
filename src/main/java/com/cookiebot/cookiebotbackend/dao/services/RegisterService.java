package com.cookiebot.cookiebotbackend.dao.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cookiebot.cookiebotbackend.core.domains.Register;
import com.cookiebot.cookiebotbackend.core.domains.UserRegister;
import com.cookiebot.cookiebotbackend.dao.repository.RegisterRepository;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.BadRequestException;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.ObjectNotFoundException;

@Service
public class RegisterService {
	
	private final RegisterRepository repository;
	
	public RegisterService(RegisterRepository repository) {
		this.repository = repository;
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
		Register register = repository.findById(id).orElseThrow(ObjectNotFoundException::new);
		
		List<UserRegister> userList = new ArrayList<>(register.getUsers());
		Integer userSize = userList.size();
		
		for (int userArray = userSize - 1; userArray >= 0; userArray--) {
			String currentUser = userList.get(userArray).getUser();
			if (currentUser == null) {
				continue;
			}
			if (currentUser.matches(user.getUser())) {
				throw new BadRequestException("User Already Exists");
			}
		}
		
		userList.addAll(Arrays.asList(user));
		register.setUsers(userList);
		repository.save(register);
	}
	
	public void deleteUser(String id, UserRegister user) {
		if (user.getUser() == null) {
			throw new BadRequestException("User Must Not Be Null");
		}
		Register register = repository.findById(id).orElseThrow(ObjectNotFoundException::new);
		
		List<UserRegister> userList = new ArrayList<>(register.getUsers());
		String userToDelete = user.getUser();
		Integer userSize = userList.size();
		
		boolean foundUser = false;
		for (int userArray = userSize - 1; userArray >= 0; userArray--) {
			String currentUser = userList.get(userArray).getUser();
			if (currentUser == null) {
				continue;
			}
			if (currentUser.matches(userToDelete)) {
				foundUser = true;
				userList.remove(userArray);
				register.setUsers(userList);
				repository.save(register);
			}
		}
		
		if (!foundUser) {
			throw new ObjectNotFoundException("User Not Found");
		}
	}
	
	public void updateUser(String id, UserRegister user) {
		if (user.getUser() == null) {
			throw new BadRequestException("User Must Not Be Null");
		}
		Register register = repository.findById(id).orElseThrow(ObjectNotFoundException::new);
		
		List<UserRegister> userList = new ArrayList<>(register.getUsers());
		Integer userSize = userList.size();
		
		boolean foundUser = false;
		for (int userArray = userSize - 1; userArray >= 0; userArray--) {
			String currentUser = userList.get(userArray).getUser();
			if (currentUser == null) {
				continue;
			}
			if (currentUser.matches(user.getUser())) {
				foundUser = true;
				userList.remove(userArray);
				userList.addAll(Arrays.asList(user));
				register.setUsers(userList);
				repository.save(register);
			}
		}
		
		if (!foundUser) {
			throw new ObjectNotFoundException("User Not Found");
		}
	}
}