package com.cookiebot.cookiebotbackend.core.domains;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "rules")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rule implements Serializable {

	private static final long serialVersionUID = 6817037389185701590L;

	@Id
	private String id;
	private String rules;
}