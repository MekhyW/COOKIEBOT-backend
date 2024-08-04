package com.cookiebot.cookiebotbackend.core.domains;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "raffles")
public class Raffle {
	
	@Id
	private String name;
	private String award;
	private String deadline;
	private List<RaffleParticipant> participants = new ArrayList<>();
	
	public Raffle() {
	}
	
	public Raffle(String name, String award, String deadline, List<RaffleParticipant> participants) {
		super();
		this.name = name;
		this.award = award;
		this.deadline = deadline;
		this.participants = participants;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAward() {
		return award;
	}

	public void setAward(String award) {
		this.award = award;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public List<RaffleParticipant> getParticipants() {
		return participants;
	}

	public void setParticipants(List<RaffleParticipant> participants) {
		this.participants = participants;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Raffle raffle = (Raffle) o;

		return Objects.equals(name, raffle.name) &&
				Objects.equals(award, raffle.award) &&
				Objects.equals(deadline, raffle.deadline) &&
				Objects.equals(participants, raffle.participants);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, award, deadline, participants);
	}
}
