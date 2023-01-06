package com.cookiebot.cookiebotbackend.core.domains;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "configs")
public class Config implements Serializable {
	private static final long serialVersionUID = 1L;
	
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
	private String threadPosts;
	private Integer maxPosts;

	public Config() {	
	}

	public Config(String id, Boolean furbots, String stickerSpamLimit, Integer timeWithoutSendingImages,
			Integer timeCaptcha, Boolean functionsFun, Boolean functionsUtility, Boolean sfw, String language,
			Boolean publisherPost, Boolean publisherAsk, String threadPosts, Integer maxPosts) {
		super();
		this.id = id;
		this.furbots = furbots;
		this.stickerSpamLimit = stickerSpamLimit;
		this.timeWithoutSendingImages = timeWithoutSendingImages;
		this.timeCaptcha = timeCaptcha;
		this.functionsFun = functionsFun;
		this.functionsUtility = functionsUtility;
		this.sfw = sfw;
		this.language = language;
		this.publisherPost = publisherPost;
		this.publisherAsk = publisherAsk;
		this.threadPosts = threadPosts;
		this.maxPosts = maxPosts;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getFurbots() {
		return furbots;
	}

	public void setFurbots(Boolean furbots) {
		this.furbots = furbots;
	}

	public String getStickerSpamLimit() {
		return stickerSpamLimit;
	}

	public void setStickerSpamLimit(String stickerSpamLimit) {
		this.stickerSpamLimit = stickerSpamLimit;
	}

	public Integer getTimeWithoutSendingImages() {
		return timeWithoutSendingImages;
	}

	public void setTimeWithoutSendingImages(Integer timeWithoutSendingImages) {
		this.timeWithoutSendingImages = timeWithoutSendingImages;
	}

	public Integer getTimeCaptcha() {
		return timeCaptcha;
	}

	public void setTimeCaptcha(Integer timeCaptcha) {
		this.timeCaptcha = timeCaptcha;
	}

	public Boolean getFunctionsFun() {
		return functionsFun;
	}

	public void setFunctionsFun(Boolean functionsFun) {
		this.functionsFun = functionsFun;
	}

	public Boolean getFunctionsUtility() {
		return functionsUtility;
	}

	public void setFunctionsUtility(Boolean functionsUtility) {
		this.functionsUtility = functionsUtility;
	}

	public Boolean getSfw() {
		return sfw;
	}

	public void setSfw(Boolean sfw) {
		this.sfw = sfw;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Boolean getPublisherPost() {
		return publisherPost;
	}

	public void setPublisherPost(Boolean publisherPost) {
		this.publisherPost = publisherPost;
	}

	public Boolean getPublisherAsk() {
		return publisherAsk;
	}

	public void setPublisherAsk(Boolean publisherAsk) {
		this.publisherAsk = publisherAsk;
	}

	public String getThreadPosts() {
		return threadPosts;
	}

	public void setThreadPosts(String threadPosts) {
		this.threadPosts = threadPosts;
	}

	public Integer getMaxPosts() {
		return maxPosts;
	}

	public void setMaxPosts(Integer maxPosts) {
		this.maxPosts = maxPosts;
	}
}
