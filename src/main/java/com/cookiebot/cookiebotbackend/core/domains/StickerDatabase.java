package com.cookiebot.cookiebotbackend.core.domains;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "stickerdatabase")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StickerDatabase {

	/**
	 * The id of the sticker in the mongo database
	 */

	@Id
	private String id;
}