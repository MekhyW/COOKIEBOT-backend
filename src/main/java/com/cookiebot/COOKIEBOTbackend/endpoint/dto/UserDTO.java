package com.cookiebot.COOKIEBOTbackend.endpoint.dto;

import java.io.Serializable;

import com.cookiebot.COOKIEBOTbackend.endpoint.domain.User;

public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;

	public UserDTO() {
	}
	
	public UserDTO(User obj) {
		id = obj.getId();
		name = obj.getName();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
