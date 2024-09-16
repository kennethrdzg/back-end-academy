package com.kennethrdzg.smalltalk.service;

import com.kennethrdzg.smalltalk.entities.User;

public interface UserService {
    public User getUserById(int id) throws RuntimeException;
    public User getUserByUsername(String username) throws RuntimeException;
    public User register(User user) throws RuntimeException;
    // public String getSalt(String username) throws RuntimeException;
    public User login(User user) throws RuntimeException;
}
