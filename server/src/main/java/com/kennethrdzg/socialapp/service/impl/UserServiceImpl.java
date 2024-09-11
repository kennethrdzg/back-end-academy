package com.kennethrdzg.socialapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.kennethrdzg.socialapp.entities.User;
import com.kennethrdzg.socialapp.repository.UserRepository;
import com.kennethrdzg.socialapp.service.UserService;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public boolean register(User user){
        try{
            User result = userRepository.save(user);
            return result.getId() > 0;
        }
        catch(DataIntegrityViolationException e){
            System.err.println("Oops!");
            return false;
        }
    }

    @Override
    public boolean login(User user){
        List<User> users = userRepository.findAll();
        boolean result = users.stream()
            .anyMatch(
                u -> u.getUsername().compareTo(user.getUsername()) == 0 && u.getPasswd().compareTo(user.getPasswd()) == 0
            );
        return result;
    }
}
