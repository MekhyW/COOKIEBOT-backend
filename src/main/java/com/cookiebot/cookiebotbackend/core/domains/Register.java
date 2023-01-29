package com.cookiebot.cookiebotbackend.core.domains;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "registers")
public class Register implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private List<UserRegister> users = new ArrayList<>();
	
	public Register() {
	}
	
	public Register(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<UserRegister> getUsers() {
		return users;
	}

	public void setUsers(List<UserRegister> users) {
		this.users = users;
	}
}
