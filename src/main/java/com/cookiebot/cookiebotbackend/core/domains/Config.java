package com.cookiebot.cookiebotbackend.core.domains;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "configs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Config implements Serializable {

	private static final long serialVersionUID = 5830653282136154429L;
	
	@Id
	private String id;
	private Boolean furbots;
	private String stickerSpamLimit;
	private Integer timeWithoutSendingImages;
	private Integer timeCaptcha;
	private Boolean functionsFun;
	private Boolean functionsUtility;
	private Boolean sfw;
	private String language;
	private Boolean publisherPost;
	private Boolean publisherAsk;
	private Boolean publisherMembersOnly;
	private String threadPosts;
	private Integer maxPosts;
}