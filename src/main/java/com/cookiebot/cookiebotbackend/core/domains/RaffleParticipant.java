package com.cookiebot.cookiebotbackend.core.domains;

import java.io.Serializable;

public class RaffleParticipant implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String user;
	
	public RaffleParticipant() {
	}

	public RaffleParticipant(String user) {
		super();
		this.user = user;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}
