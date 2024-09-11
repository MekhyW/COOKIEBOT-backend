package com.cookiebot.cookiebotbackend.core.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RaffleParticipant {

	public static final String USER_FIELD = "user";

	private String user;
}