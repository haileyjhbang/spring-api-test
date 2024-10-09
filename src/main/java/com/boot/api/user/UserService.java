package com.boot.api.user;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    public long getCount(){
        List<User> users = getUsers("user.json");
        return users.stream().mapToLong(User::getPostCount).sum();
    }

    private List<User> getUsers(String fileName){
        return FileUtils.getListFromJson(fileName, User.class);

    }

}