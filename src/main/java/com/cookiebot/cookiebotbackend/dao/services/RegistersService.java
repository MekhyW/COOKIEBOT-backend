package com.cookiebot.cookiebotbackend.dao.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cookiebot.cookiebotbackend.core.domain.Registers;
import com.cookiebot.cookiebotbackend.core.domain.UserRegisters;
import com.cookiebot.cookiebotbackend.core.repository.RegistersRepository;
import com.cookiebot.cookiebotbackend.dao.services.exception.BadRequestException;
import com.cookiebot.cookiebotbackend.dao.services.exception.ObjectNotFoundException;

@Service
public class RegistersService {

	@Autowired
	
	private RegistersRepository repo;
	
	public List<Registers> findAll(){
		return repo.findAll();
	}
	
	public Registers findById(String id) {
		Registers registers = repo.findById(id).orElseThrow(() -> new ObjectNotFoundException("Object Not Found"));
		return registers;
	}
	
	public Registers insert(Registers registers) {
		Registers searchId = repo.findById(registers.getId()).orElse(null);
		if (searchId == null) {
			return repo.insert(registers);
		} else {
			throw new BadRequestException("Id Already Exists");
		}
	}

	public void delete(String id) {
		repo.deleteById(id);
	}
	
	public void insertUser(String id, UserRegisters userRegisters) {
		Registers registers = findById(id);
		List<UserRegisters> user = new ArrayList<UserRegisters>(registers.getUsers());
		boolean foundExistingUser = false;
		Integer userSize = user.size();
		
		for (int userArray = userSize-1; userArray >= 0; userArray--) {
			if (user.get(userArray).getUser().matches(userRegisters.getUser())){
				foundExistingUser = true;
			}
		}
	
		if (foundExistingUser == false) {
			user.addAll(Arrays.asList(userRegisters));
			registers.setUsers(user);
			repo.save(registers);
		} else {
			throw new BadRequestException("User Already Exists");
		}
	}

	public void deleteUser(String id, UserRegisters userRegisters) {
		Registers registers = findById(id);
		List<UserRegisters> user = new ArrayList<UserRegisters>(registers.getUsers());
		boolean foundExistingUser = false;
		String userToDelete = userRegisters.getUser();
		Integer userSize = user.size();
		
		for (int userArray = userSize-1; userArray >= 0; userArray--) {
			
			if (user.get(userArray).getUser().matches(userToDelete)){
				foundExistingUser = true;
				user.remove(userArray);
				registers.setUsers(user);
				repo.save(registers);
			} 
		}
		
		if (foundExistingUser == false) {
			throw new ObjectNotFoundException("User Not Found");
		}
	}
	
	public void updateUser(String id, UserRegisters userRegisters) {
		Registers registers = findById(id);
		List<UserRegisters> user = new ArrayList<UserRegisters>(registers.getUsers());
		boolean foundExistingUser = false;
		Integer userSize = user.size();
		
		for (int userArray = userSize-1; userArray >= 0; userArray--) {
			
			if (user.get(userArray).getUser().matches(userRegisters.getUser())){
				foundExistingUser = true;
				user.remove(userArray);
				user.addAll(Arrays.asList(userRegisters));
				registers.setUsers(user);
				repo.save(registers);
			} 
		}
		
		if (foundExistingUser == false) {
			throw new ObjectNotFoundException("User Not Found");
		}
	}
}