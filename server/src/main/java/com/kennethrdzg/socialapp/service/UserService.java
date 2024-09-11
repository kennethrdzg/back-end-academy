package com.kennethrdzg.socialapp.service;

import com.kennethrdzg.socialapp.entities.User;

public interface UserService {
    public boolean register(User user);
    public boolean login(User user);
}
