package com.boot.api.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @JsonProperty("user_id")
    private  int userId;
    @JsonProperty("username")
    private  String userName;
    @JsonProperty("post_count")
    private  int postCount;
}

// public  record User( @JsonProperty("user_id") int userId,  @JsonProperty("username") String userName,  @JsonProperty("post_count") int postCount) {
// }
