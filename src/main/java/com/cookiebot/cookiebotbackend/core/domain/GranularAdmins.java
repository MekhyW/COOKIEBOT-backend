package com.cookiebot.cookiebotbackend.core.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "granularadmins")
public class GranularAdmins implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private String user;
	
	public GranularAdmins() {
	}

	public GranularAdmins(String id, String user) {
		super();
		this.id = id;
		this.user = user;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}