package com.cookiebot.cookiebotbackend.core.domains;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "stickerdatabase")
public class StickerDatabase {
	@Id
	private String id;
	
	public StickerDatabase() {
	}

	public StickerDatabase(String id) {
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
