package com.cookiebot.cookiebotbackend.core.domain;

import java.io.Serializable;

public class UserRegisters implements Serializable {
	private static final long serialVersionUID = 1L;

	private String user;
	private String date;
	
	public UserRegisters() {
	}
	
	public UserRegisters(String user, String date) {
		super();
		this.user = user;
		this.date = date;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
}
