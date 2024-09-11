package com.cookiebot.cookiebotbackend.core.domains;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "stickers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sticker implements Serializable {
	
	private static final long serialVersionUID = 2448708178052974006L;

	/**
	 * The id of the sticker in the mongo database.
	 */

	@Id
	private String id;

	/**
	 * The last time the sticker was used.
	 */

	private String lastUsed;
}