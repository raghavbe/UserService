package com.capstone.userservice.services;

import com.capstone.userservice.models.Token;
import com.capstone.userservice.models.User;
import org.springframework.stereotype.Service;

public interface UserService
{
    User signup(String name, String email, String password);
    Token login(String email, String password);
    void logout(Token token);
    User validateToken(String tokenValue);
}