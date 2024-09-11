package com.kennethrdzg.socialapp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kennethrdzg.socialapp.entities.User;
import com.kennethrdzg.socialapp.service.UserService;

@RestController
@RequestMapping("/users")
public class UserRestController{
    private UserService userService;

    @Autowired
    public UserRestController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    String test(){
        return "It works!";
    }

    @PostMapping("/register")
    boolean registerUser(@RequestBody User user){
        user.setId(0);
        return userService.register(user);
    }
    @PostMapping("/login")
    boolean login(@RequestBody User user){
        return userService.login(user);
    }
}
