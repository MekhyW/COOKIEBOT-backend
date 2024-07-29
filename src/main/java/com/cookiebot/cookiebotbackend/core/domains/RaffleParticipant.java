package com.cookiebot.cookiebotbackend.core.domains;

import java.util.Objects;

public class RaffleParticipant {
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RaffleParticipant that = (RaffleParticipant) o;
		return Objects.equals(user, that.user);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(user);
	}
}
