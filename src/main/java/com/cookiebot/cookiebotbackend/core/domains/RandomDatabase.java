package com.cookiebot.cookiebotbackend.core.domains;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "randomdatabase")
public class RandomDatabase implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private String idMessage;
	private String idMedia;
	
	public RandomDatabase() {
		
	}

	public RandomDatabase(String id, String idMessage, String idMedia) {
		super();
		this.id = id;
		this.idMessage = idMessage;
		this.idMedia = idMedia;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdMessage() {
		return idMessage;
	}

	public void setIdMessage(String idMessage) {
		this.idMessage = idMessage;
	}

	public String getIdMedia() {
		return idMedia;
	}

	public void setIdMedia(String idMedia) {
		this.idMedia = idMedia;
	}
}