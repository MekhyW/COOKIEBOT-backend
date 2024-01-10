package com.cookiebot.cookiebotbackend.core.domains;

import java.io.Serializable;

public class UserRegister implements Serializable {
	private static final long serialVersionUID = 1L;

	private String user;
	private String date;
	private String accountId;
	
	public UserRegister() {
	}

	public UserRegister(String user, String date, String accountId) {
		super();
		this.user = user;
		this.date = date;
		this.accountId = accountId;
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

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
}