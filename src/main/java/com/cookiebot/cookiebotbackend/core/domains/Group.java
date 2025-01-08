package com.cookiebot.cookiebotbackend.core.domains;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "groups")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Group {
    public static final String GROUP_ID_FIELD = "groupId";
    public static final String ADMIN_USERS_FIELD = "adminUsers";

    @Indexed(unique = true)
    private String groupId;
    private Set<String> adminUsers = new HashSet<>();
}
