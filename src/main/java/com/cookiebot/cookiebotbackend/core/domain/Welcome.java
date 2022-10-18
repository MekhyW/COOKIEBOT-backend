package com.cookiebot.cookiebotbackend.core.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="welcomes")
public class Welcome implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private String message;
	
	public Welcome(String id, String message) {
		super();
		this.id = id;
		this.message = message;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
