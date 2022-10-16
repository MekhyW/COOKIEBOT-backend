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
		Registers registers = repo.findById(id).orElseThrow(() -> new ObjectNotFoundException("Object Id not found!"));
		return registers;
	}
	
	public Registers insert(Registers obj) {
		Registers searchId = repo.findById(obj.getId()).orElse(null);
		if (searchId != null) {
			throw new BadRequestException("Id already exists!");
		}
		return repo.insert(obj);
	}
	
	public void delete(String id) {
		repo.deleteById(id);
	}
	
	public void insertUser(String id, UserRegisters obj) {
		Registers registers = findById(id);
		List<UserRegisters> user = new ArrayList<UserRegisters>(registers.getUsers());
		user.addAll(Arrays.asList(obj));
		registers.setUsers(user);
		repo.save(registers);
	}
	
	public void deleteUser(String id, UserRegisters obj) {
		Registers registers = findById(id);
		List<UserRegisters> user = new ArrayList<UserRegisters>(registers.getUsers());
		String userToDelete = obj.getUser();
		
		Integer userSize = user.size();
		for (int userArray = userSize-1; userArray >= 0; userArray--) {
			if (user.get(userArray).getUser().matches(userToDelete)){
				user.remove(userArray);
			}
		}
		
		registers.setUsers(user);
		repo.save(registers);
	}
}