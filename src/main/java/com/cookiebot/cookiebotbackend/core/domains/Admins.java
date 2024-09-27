package com.cookiebot.cookiebotbackend.core.domains;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "admins")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admins {



    @Id
    private String groupId;
    private List<User> adminUsers = new ArrayList<>();
}
