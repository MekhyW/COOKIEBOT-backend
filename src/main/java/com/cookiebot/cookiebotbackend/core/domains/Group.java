package com.cookiebot.cookiebotbackend.core.domains;

import java.util.HashSet;
import java.util.Set;

import lombok.Builder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "groups")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Group {
    public static final String GROUP_ID_FIELD = "groupId";
    public static final String ADMIN_USERS_FIELD = "adminUsers";

    @Indexed(unique = true)
    private String groupId;
    private String name;
    private String imageUrl;
    
    @Builder.Default
    private Set<String> adminUsers = new HashSet<>();
}
