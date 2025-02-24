package com.cookiebot.cookiebotbackend.core.domains;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegister implements Serializable {

	private static final long serialVersionUID = 5499558587374866643L;
	public static final String USER_FIELD = "user";
	
	private String user;
	private String date;
	private String accountId;
	
}