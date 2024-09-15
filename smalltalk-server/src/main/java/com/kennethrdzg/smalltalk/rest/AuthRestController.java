package com.kennethrdzg.smalltalk.rest;

import java.util.Date;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.kennethrdzg.smalltalk.entities.User;
import com.kennethrdzg.smalltalk.entities.UserToken;
import com.kennethrdzg.smalltalk.service.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class AuthRestController{
    private UserService userService;
    private String secret_key;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Autowired
    private AmqpAdmin admin;

    @Autowired
    public AuthRestController(UserService userService){
        this.userService = userService;
        this.secret_key = System.getenv("APP_SECRET_KEY");
    }

    private String createToken(String username) throws RuntimeException{
        try{
            Date date = new Date();
            date.setTime(date.getTime() + 30L*24L*60L*60L*1000L);
            Algorithm algorithm = Algorithm.HMAC256(secret_key);
            String token = JWT.create()
                .withClaim("username", username)
                .withExpiresAt(date)
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
            System.err.println("Username \"" + user.getUsername() + "\" is already taken");
            throw new RuntimeException(e.getMessage());
        }

        try{
            Queue queue = new Queue(user.getUsername());
            Binding binding = new Binding(user.getUsername(), Binding.DestinationType.QUEUE, exchange, String.valueOf(user.getId()), null);
            admin.declareQueue(queue);
            admin.declareBinding(binding);
            return new UserToken(user.getId(), user.getUsername(), createToken(user.getUsername()));
        } catch(RuntimeException e){
            System.err.println("Could not authenticate user");
            throw new RuntimeException();
        }
    }

    // @GetMapping("/login/{username}")
    // public String getSalt(@PathVariable String username) throws RuntimeException{
    //     try{
    //         return userService.getSalt(username);
    //     } catch(RuntimeException e){
    //         System.err.println("User \"" + username + "\" does not exist");
    //         throw new RuntimeException(e.getMessage());
    //     }
    // }

    @PostMapping("/login")
    public UserToken login(@RequestBody User user) throws RuntimeException{
        try {
            user = userService.login(user);
        } catch(RuntimeException e){
            System.err.println("Incorrect username or password");
            throw new RuntimeException(e.getMessage());
        }
        try{
            Queue queue = new Queue(user.getUsername());
            Binding binding = new Binding(user.getUsername(), Binding.DestinationType.QUEUE, exchange, String.valueOf(user.getId()), null);
            admin.declareQueue(queue);
            admin.declareBinding(binding);
            return new UserToken(user.getId(), user.getUsername(), createToken(user.getUsername()));
        } catch(RuntimeException e){
            System.err.println("Could not create authentication token");
            throw new RuntimeException();
        }
    }
}
