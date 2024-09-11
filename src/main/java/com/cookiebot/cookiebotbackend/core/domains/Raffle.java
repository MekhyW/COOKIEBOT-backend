package com.cookiebot.cookiebotbackend.core.domains;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "raffles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Raffle {
	
	@Id
	private String name;
	private String award;
	private String deadline;
	private List<RaffleParticipant> participants = new ArrayList<>();
}