package com.cookiebot.cookiebotbackend.core.domains;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "stickers")
public class Sticker implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private String lastUsed;
	
	public Sticker() {	
	}

	public Sticker(String id, String lastUsed) {
		super();
		this.id = id;
		this.lastUsed = lastUsed;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLastUsed() {
		return lastUsed;
	}

	public void setLastUsed(String lastUsed) {
		this.lastUsed = lastUsed;
	}
}
