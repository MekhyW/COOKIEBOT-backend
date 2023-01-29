package com.cookiebot.cookiebotbackend.core.domains;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "blacklist")
public class Blacklist implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;

	public Blacklist() {
	}
	
	public Blacklist(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
