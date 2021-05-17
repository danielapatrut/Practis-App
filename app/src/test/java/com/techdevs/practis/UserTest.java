package com.techdevs.practis;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void getUserName() {
        User user = new User("username","email","password");
        String username = user.getUserName();
        assertEquals("username",username);
    }

    @Test
    public void setUserName() {
        User user = new User();
        user.setUserName("username");
        assertEquals("username",user.getUserName());
    }

    @Test
    public void getEmail() {
        User user = new User("username","email","password");
        String email = user.getEmail();
        assertEquals("email",email);
    }

    @Test
    public void setEmail() {
        User user = new User();
        user.setEmail("email");
        assertEquals("email",user.getEmail());
    }

    @Test
    public void getPassword() {
        User user = new User("username","email","password");
        String password = user.getPassword();
        assertEquals("password",password);
    }

    @Test
    public void setPassword() {
        User user = new User();
        user.setPassword("password");
        assertEquals("password",user.getPassword());
    }
}