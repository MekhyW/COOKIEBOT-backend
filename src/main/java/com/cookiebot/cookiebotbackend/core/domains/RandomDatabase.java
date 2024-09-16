package com.cookiebot.cookiebotbackend.core.domains;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "randomdatabase")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RandomDatabase implements Serializable {

	private static final long serialVersionUID = 3631867864777970110L;

	@Id
	private String id;
	private String idMessage;
	private String idMedia;
}