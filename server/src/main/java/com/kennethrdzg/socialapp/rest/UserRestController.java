package com.kennethrdzg.socialapp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.kennethrdzg.socialapp.entities.User;
import com.kennethrdzg.socialapp.entities.UserToken;
import com.kennethrdzg.socialapp.service.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserRestController{
    private UserService userService;
    private String secret_key;

    @Autowired
    public UserRestController(UserService userService){
        this.userService = userService;
        secret_key = "SECRET_KEY";
    }

    private String createToken(String username) throws RuntimeException{
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret_key);
            String token = JWT.create()
                .withClaim("username", username)
                .withIssuer("com.kennethrdzg")
                .sign(algorithm);
            return token;
        } catch (JWTCreationException e){
            throw new RuntimeException();
        }
    }

    @PostMapping("/register")
    public UserToken registerUser(@RequestBody User user) throws RuntimeException{
        user.setId(0);
        try{
            user = userService.register(user);
        }
        catch(RuntimeException e){
            System.err.println("Error registering user");
            throw new RuntimeException(e.getMessage());
        }

        try{
            return new UserToken(user.getUsername(), createToken(user.getUsername()));
        } catch(RuntimeException e){
            System.err.println("Could not create authentication token");
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/login/{username}")
    public String getSalt(@PathVariable String username) throws RuntimeException{
        try{
            return userService.getSalt(username);
        } catch(RuntimeException e){
            System.err.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping("/login")
    public UserToken login(@RequestBody User user) throws RuntimeException{
        try {
            user = userService.login(user);
        } catch(RuntimeException e){
            System.err.println("Error logging in");
            throw new RuntimeException(e.getMessage());
        }
        try{
            return new UserToken(user.getUsername(), createToken(user.getUsername()));
        } catch(RuntimeException e){
            System.err.println("Could not create authentication token");
            throw new RuntimeException(e.getMessage());
        }
    }
}
