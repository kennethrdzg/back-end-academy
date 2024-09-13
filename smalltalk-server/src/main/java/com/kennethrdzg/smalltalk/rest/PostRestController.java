package com.kennethrdzg.smalltalk.rest;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kennethrdzg.smalltalk.dto.PostDTO;
import com.kennethrdzg.smalltalk.entities.Post;
import com.kennethrdzg.smalltalk.entities.User;
import com.kennethrdzg.smalltalk.service.PostService;
import com.kennethrdzg.smalltalk.service.UserService;

@RestController
@RequestMapping("/posts")
@CrossOrigin
public class PostRestController {
    private PostService postService;
    private UserService userService;
    private String secretKey;

    @Autowired
    public PostRestController(PostService postService, UserService userService){
        this.postService = postService;
        this.userService = userService;
        this.secretKey = System.getenv("APP_SECRET_KEY");
    }

    @GetMapping
    public List<Post> getPosts(){
        return postService.getPosts();
    }

    @GetMapping("/page/{page}")
    public List<Post> getPostsByPage(@PathVariable int page) throws RuntimeException{
        if(page < 1)
            page = 1;
        try{
            return postService.getPosts(page);
        } catch(RuntimeException e){
            System.err.println("Invalid page number");
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/{postId}")
    public Post getPostById(@PathVariable int postId) throws RuntimeException{
        try{
            return postService.getPostById(postId);
        } catch(RuntimeException e){
            System.err.println("Post with ID #" + postId + " does not exist");
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public List<Post> getPostsByUserId(@PathVariable int userId) throws RuntimeException{
        try{
            userService.getUserById(userId);
        }
        catch(RuntimeException e){
            System.err.println("User with ID #" + userId + " does not exist");
            throw new RuntimeException();
        }
        return postService.getPostsByUserId(userId);
    }

    @PostMapping("/upload")
    public Post uploadPost(@RequestBody PostDTO postDTO) throws RuntimeException{
        System.out.println(postDTO);

        User user;
        try{
            user = userService.getUserByUsername(postDTO.getUsername());
        } catch(RuntimeException e){
            System.err.println("User does not exist");
            throw new RuntimeException(e.getMessage());
        }

        DecodedJWT decodedJWT;
        try{
            Algorithm algorithm = Algorithm.HMAC256(this.secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("com.kennethrdzg")
                .withClaim("username", user.getUsername())
                .build();
            decodedJWT = verifier.verify(postDTO.getToken());
        } catch( JWTVerificationException exception){
            System.err.println("Invalid JWT");
            throw new RuntimeException(exception.getMessage());
        }

        System.out.println("Current time: " + new Date().getTime());
        System.out.println("Token expiration: " + decodedJWT.getExpiresAt().getTime());

        Post post = new Post();
        post.setId(0);
        post.setUserId(user.getId());
        post.setContent(postDTO.getContent());
        post.setTimestamp(LocalDateTime.now());

        try{
            return postService.uploadPost(post);
        } catch(RuntimeException e){
            System.err.println("Could not upload post");
            throw new RuntimeException(e.getMessage());
        }
    }
}
