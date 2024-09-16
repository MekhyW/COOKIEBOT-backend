package com.cookiebot.cookiebotbackend.core.domains;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "blacklist")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blacklist implements Serializable {
	
	private static final long serialVersionUID = 2087294201832990171L;
	
	@Id
	private String id;
}