package com.cookiebot.cookiebotbackend.core.domains;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {


    @Id
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String languageCode;
    private LocalDate birthdate;
}
