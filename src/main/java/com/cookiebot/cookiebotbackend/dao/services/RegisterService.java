package com.cookiebot.cookiebotbackend.dao.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cookiebot.cookiebotbackend.core.domain.Register;
import com.cookiebot.cookiebotbackend.core.domain.UserRegister;
import com.cookiebot.cookiebotbackend.dao.repository.RegisterRepository;
import com.cookiebot.cookiebotbackend.dao.services.exception.BadRequestException;
import com.cookiebot.cookiebotbackend.dao.services.exception.ObjectNotFoundException;

@Service
public class RegisterService {

	@Autowired
	private RegisterRepository repository;
	
	public List<Register> findAll(){
		return repository.findAll();
	}
	
	public Register findById(String id) {
		Register register = repository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Object Not Found"));
		return register;
	}
	
	public Register insert(String id, Register register) {
		
		if (repository.findById(id).orElse(null) != null) {
			throw new BadRequestException("ID Already Exists");	
		} 
		
		register.setId(id);
		return repository.insert(register);
	}

	public void delete(String id) {
		try {
			repository.deleteById(id);
		} catch (Exception e) {
			throw new BadRequestException("ID Must Not Be Null");
		}
	}
	
	public void insertUser(String id, UserRegister user) {
		
		if (user.getUser() == null) {
			throw new BadRequestException("User Must Not Be Null");
		}
		
		Register register = repository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Object Not Found"));
		List<UserRegister> userList = new ArrayList<UserRegister>(register.getUsers());
		Integer userSize = userList.size();
		
		for (int userArray = userSize-1; userArray >= 0; userArray--) {
			if (userList.get(userArray).getUser().matches(user.getUser())){
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
		
		Register register = repository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Object Not Found"));
		List<UserRegister> userList = new ArrayList<UserRegister>(register.getUsers());
		String userToDelete = user.getUser();
		Integer userSize = userList.size();
		
		boolean foundUser = false;
		for (int userArray = userSize-1; userArray >= 0; userArray--) {
			if (userList.get(userArray).getUser().matches(userToDelete)){	
				foundUser = true;
				userList.remove(userArray);
				register.setUsers(userList);
				repository.save(register);
			} 
		}
		
		if (foundUser == false) {
			throw new ObjectNotFoundException("User Not Found");
		}
	}
	
	public void updateUser(String id, UserRegister user) {
		
		if (user.getUser() == null) {
			throw new BadRequestException("User Must Not Be Null");
		}
		
		Register register = repository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Object Not Found"));
		List<UserRegister> userList = new ArrayList<UserRegister>(register.getUsers());
		Integer userSize = userList.size();
		
		boolean foundUser = false;
		for (int userArray = userSize-1; userArray >= 0; userArray--) {	
			if (userList.get(userArray).getUser().matches(user.getUser())){
				foundUser = true;
				userList.remove(userArray);
				userList.addAll(Arrays.asList(user));
				register.setUsers(userList);
				repository.save(register);
			} 
		}
		
		if (foundUser == false) {
			throw new ObjectNotFoundException("User Not Found");
		}
	}
}