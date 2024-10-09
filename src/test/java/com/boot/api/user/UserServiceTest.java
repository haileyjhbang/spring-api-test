package com.boot.api.user;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

class UserServiceTest {

    private final UserService userService = new UserService();

    @Test
    void getUsers__success(){
        //given
        //when
        List<User> users = FileUtils.getListFromJson("user.json", User.class);
        //then
        assertEquals(50, users.size());
        assertEquals(924, users.get(0).getUserId());
    }

    @Test
    void getSum__success(){
        //given
        //when
       long count =userService.getCount();
        //then
        assertEquals(2416, count);
    }

}
