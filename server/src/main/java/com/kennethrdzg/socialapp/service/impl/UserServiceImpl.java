package com.kennethrdzg.socialapp.service.impl;

import java.util.Optional;

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
    public User register(User user){
        try{
            User result = userRepository.save(user);
            return result;
        }
        catch(DataIntegrityViolationException e){
            System.err.println("Oops!");
            return null;
        }
    }

    @Override
    public User login(User user){
        Optional<User> result =  userRepository.findAll()
            .stream()
            .filter(
                (u) ->
                u.getUsername().compareTo(user.getUsername()) == 0
                && u.getPasswd().compareTo(user.getPasswd()) == 0
            ).findFirst();

        return result.isPresent() ? result.get(): null;
    }
}
