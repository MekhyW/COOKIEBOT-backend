package com.cookiebot.cookiebotbackend.core.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "registers")
public class Registers implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private List<UserRegisters> users = new ArrayList<>();
	
	public Registers() {
	}
	
	public Registers(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<UserRegisters> getUsers() {
		return users;
	}

	public void setUsers(List<UserRegisters> users) {
		this.users = users;
	}
}
