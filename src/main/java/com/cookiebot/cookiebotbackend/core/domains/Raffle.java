package com.cookiebot.cookiebotbackend.core.domains;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "raffles")
public class Raffle implements Serializable {
	private static final long serialVersionUID = 1L;
	
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
}
