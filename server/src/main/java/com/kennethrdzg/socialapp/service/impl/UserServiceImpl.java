package com.kennethrdzg.socialapp.service.impl;

import java.util.NoSuchElementException;
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

    public User getUserById(int id) throws RuntimeException{
        Optional<User> user = this.userRepository.findById(id);
        try{
            return user.orElseThrow();
        } catch(NoSuchElementException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public User getUserByUsername(String username) throws RuntimeException{
        Optional<User> user = this.userRepository.findAll()
            .stream()
            .filter( (u) -> u.getUsername().compareTo(username) == 0)
            .findFirst();
            try{
                return user.orElseThrow();
            } catch(NoSuchElementException e){
                throw new RuntimeException(e.getMessage());
            }
    }

    @Override
    public User register(User user) throws RuntimeException{
        String username = user.getUsername();
        boolean usernameExists = userRepository.findAll()
            .stream()
            .anyMatch( (u) -> u.getUsername().compareTo(username) == 0);
        if(!usernameExists){
            try{
                user = userRepository.save(user);
                return user;
            }
            catch(DataIntegrityViolationException e){
                throw new RuntimeException(e.getMessage());
            }
        }
        return null;
    }

    // @Override
    // public String getSalt(String username) throws RuntimeException{
    //     Optional<User> optUser = userRepository.findAll()
    //         .stream()
    //         .filter(
    //             (u) -> u.getUsername().compareTo(username) == 0
    //         ).findFirst();
    //     User user;
    //     try{
    //         user = optUser.orElseThrow();
    //     } catch(NoSuchElementException e){
    //         throw new RuntimeException(e.getMessage());
    //     }
    //     return user.getSalt();
    // }

    @Override
    public User login(User user) throws RuntimeException{
        Optional<User> result =  userRepository.findAll()
            .stream()
            .filter(
                (u) ->
                u.getUsername().compareTo(user.getUsername()) == 0
                && u.getPasswordHash().compareTo(user.getPasswordHash()) == 0
            ).findFirst();
        
        try{
            return result.orElseThrow();
        }
        catch(NoSuchElementException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
