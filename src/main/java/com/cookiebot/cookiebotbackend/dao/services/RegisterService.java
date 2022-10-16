package com.cookiebot.cookiebotbackend.dao.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cookiebot.cookiebotbackend.core.domain.Register;
import com.cookiebot.cookiebotbackend.core.domain.UserRegister;
import com.cookiebot.cookiebotbackend.core.repository.RegisterRepository;
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
		
		Register register = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Object Not Found"));
		return register;
	}
	
	public Register insert(Register register) {
		
		if (register.getId() == null) {
			throw new BadRequestException("ID Must Not Be Null");
		}
		
		Register id = repository.findById(register.getId()).orElse(null);
		
		if (id == null) {
			return repository.insert(register);
		} else {
			throw new BadRequestException("ID Already Exists");
		}
	}

	public void delete(String id) {
		
		if(id == null) {
			throw new BadRequestException("ID Must Not Be Null");
		}
		
		repository.deleteById(id);
	}
	
	public void insertUser(String id, UserRegister userRegister) {
		
		if (userRegister.getUser() == null) {
			throw new BadRequestException("User Must Not Be Null");
		}
		
		Register register = findById(id);
		List<UserRegister> user = new ArrayList<UserRegister>(register.getUsers());
		boolean foundExistingUser = false;
		Integer userSize = user.size();
		
		for (int userArray = userSize-1; userArray >= 0; userArray--) {
			if (user.get(userArray).getUser().matches(userRegister.getUser())){
				foundExistingUser = true;
			}
		}
	
		if (foundExistingUser == false) {
			user.addAll(Arrays.asList(userRegister));
			register.setUsers(user);
			repository.save(register);
		} else {
			throw new BadRequestException("User Already Exists");
		}
	}

	public void deleteUser(String id, UserRegister userRegister) {
		
		if (userRegister.getUser() == null) {
			throw new BadRequestException("User Must Not Be Null");
		}
		
		Register register = findById(id);
		List<UserRegister> user = new ArrayList<UserRegister>(register.getUsers());
		boolean foundExistingUser = false;
		String userToDelete = userRegister.getUser();
		Integer userSize = user.size();
		
		for (int userArray = userSize-1; userArray >= 0; userArray--) {
			
			if (user.get(userArray).getUser().matches(userToDelete)){
				foundExistingUser = true;
				user.remove(userArray);
				register.setUsers(user);
				repository.save(register);
			} 
		}
		
		if (foundExistingUser == false) {
			throw new ObjectNotFoundException("User Not Found");
		}
	}
	
	public void updateUser(String id, UserRegister userRegister) {
		
		if (userRegister.getUser() == null) {
			throw new BadRequestException("User Must Not Be Null");
		}
		
		Register register = findById(id);
		List<UserRegister> user = new ArrayList<UserRegister>(register.getUsers());
		boolean foundExistingUser = false;
		Integer userSize = user.size();
		
		for (int userArray = userSize-1; userArray >= 0; userArray--) {
			
			if (user.get(userArray).getUser().matches(userRegister.getUser())){
				foundExistingUser = true;
				user.remove(userArray);
				user.addAll(Arrays.asList(userRegister));
				register.setUsers(user);
				repository.save(register);
			} 
		}
		
		if (foundExistingUser == false) {
			throw new ObjectNotFoundException("User Not Found");
		}
	}
}