package com.kennethrdzg.socialapp.service;

import com.kennethrdzg.socialapp.entities.User;

public interface UserService {
    public User register(User user);
    public User login(User user);
}
