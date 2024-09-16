package com.cookiebot.cookiebotbackend.core.domains;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "welcomes")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Welcome implements Serializable {
	
	private static final long serialVersionUID = 1545094678909113967L;

	@Id
	private String id;
	private String message;
}