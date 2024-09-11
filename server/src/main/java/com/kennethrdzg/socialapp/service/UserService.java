package com.kennethrdzg.socialapp.service;

import com.kennethrdzg.socialapp.entities.User;

public interface UserService {
    public User register(User user) throws RuntimeException;
    public String getSalt(String username) throws RuntimeException;
    public User login(User user) throws RuntimeException;
}
