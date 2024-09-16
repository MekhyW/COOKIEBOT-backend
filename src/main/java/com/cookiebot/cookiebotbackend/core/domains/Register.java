package com.cookiebot.cookiebotbackend.core.domains;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "registers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Register implements Serializable {

	private static final long serialVersionUID = 8563882545466215947L;

	@Id
	private String id;
	private List<UserRegister> users = new ArrayList<>();
}