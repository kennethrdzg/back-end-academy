package com.kennethrdzg.smalltalk.rest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.kennethrdzg.smalltalk.dto.PostDTO;
import com.kennethrdzg.smalltalk.entities.Notification;
import com.kennethrdzg.smalltalk.entities.Post;
import com.kennethrdzg.smalltalk.entities.PostLike;
import com.kennethrdzg.smalltalk.entities.User;
import com.kennethrdzg.smalltalk.service.NotificationService;
import com.kennethrdzg.smalltalk.service.PostLikeService;
import com.kennethrdzg.smalltalk.service.PostService;
import com.kennethrdzg.smalltalk.service.UserService;

@RestController
@RequestMapping("/posts")
@CrossOrigin
public class PostRestController {
    private PostService postService;
    private UserService userService;
    private PostLikeService postLikeService;
    @Value("${server.secret.key}")
    private String secretKey;

    private Logger LOGGER = Logger.getLogger(PostRestController.class.getName());

    private NotificationService notificationService;

    @Autowired
    public PostRestController(PostService postService, UserService userService, PostLikeService postLikeService, NotificationService notificationService){
        this.postService = postService;
        this.userService = userService;
        this.postLikeService = postLikeService;
        this.notificationService = notificationService;
    }

    @GetMapping("/{userId}/{token}")
    public List<PostDTO> getPosts(@PathVariable int userId, @PathVariable String token){
        User user;
        try{
            user = userService.getUserById(userId);
        } catch(RuntimeException e){
            System.err.println("User does not exist");
            throw new RuntimeException();
        }

        try{
            Algorithm algorithm = Algorithm.HMAC256(this.secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("com.kennethrdzg")
                .withClaim("username", user.getUsername())
                .build();
            verifier.verify(token);
        } catch( JWTVerificationException exception){
            System.err.println("Invalid JWT");
            throw new RuntimeException();
        }
        return postService.getPosts()
            .stream()
            .map(
                (post) -> {
                    return new PostDTO(
                    post.getId(),
                    post.getContent(),
                    post.getTimestamp(),
                    userService.getUserById(post.getUserId()).getUsername(), 
                    postLikeService.isLikedByUser(post.getId(), userId),
                    postLikeService.getPostLikes(post.getId()),
                    null);
                }
            ).toList();
    }

    @GetMapping("/page/{page}")
    public List<PostDTO> getPostsByPage(@PathVariable int page) throws RuntimeException{
        if(page < 1)
            page = 1;
        try{
            return postService.getPosts(page)
                    .stream()
                    .map(
                        (post) -> new PostDTO(
                            post.getId(), 
                            post.getContent(), 
                            post.getTimestamp(), 
                            userService.getUserById(post.getUserId()).getUsername(),
                            postLikeService.isLikedByUser(post.getId(), post.getUserId()), 
                            postLikeService.getPostLikes(post.getId()), 
                            null
                        )
                    ).toList();
        } catch(RuntimeException e){
            System.err.println("Invalid page number");
            throw new RuntimeException();
        }
    }

    @GetMapping("/{postId}")
    public PostDTO getPostById(@PathVariable int postId) throws RuntimeException{
        try{
            Post post =  postService.getPostById(postId);
            return new PostDTO(
                post.getId(),
                post.getContent(),
                post.getTimestamp(),
                userService.getUserById(post.getUserId()).getUsername(),
                postLikeService.isLikedByUser(post.getId(), post.getUserId()),
                postLikeService.getPostLikes(postId),
                null
                );
        } catch(RuntimeException e){
            System.err.println("Post with ID #" + postId + " does not exist");
            throw new RuntimeException();
        }
    }

    @GetMapping("/user/{userId}")
    public List<PostDTO> getPostsByUserId(@PathVariable int userId) throws RuntimeException{
        try{
            userService.getUserById(userId);
        }
        catch(RuntimeException e){
            System.err.println("User with ID #" + userId + " does not exist");
            throw new RuntimeException();
        }
        return postService.getPostsByUserId(userId)
                .stream()
                .map(
                    (post) -> new PostDTO(
                        post.getId(),
                        post.getContent(),
                        post.getTimestamp(),
                        userService.getUserById(userId).getUsername(),
                        postLikeService.isLikedByUser(post.getId(), userId),
                        postLikeService.getPostLikes(post.getId()), null
                        )
                ).toList();
    }

    @PostMapping("/upload")
    public PostDTO uploadPost(@RequestBody PostDTO postDTO) throws RuntimeException{
        User user;
        try{
            user = userService.getUserByUsername(postDTO.getUsername());
        } catch(RuntimeException e){
            System.err.println("User does not exist");
            throw new RuntimeException();
        }

        try{
            Algorithm algorithm = Algorithm.HMAC256(this.secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("com.kennethrdzg")
                .withClaim("username", user.getUsername())
                .build();
            verifier.verify(postDTO.getToken());
        } catch( JWTVerificationException exception){
            System.err.println("Invalid JWT");
            throw new RuntimeException();
        }

        Post post = new Post(
            0, postDTO.getContent(), LocalDateTime.now(), user.getId()
        );

        try{
            post = postService.uploadPost(post);
            return new PostDTO(
                post.getId(),
                post.getContent(),
                post.getTimestamp(),
                userService.getUserById(post.getUserId()).getUsername(),
                false, postLikeService.getPostLikes(post.getId()),
                null);
        } catch(RuntimeException e){
            System.err.println("Could not upload post");
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping("/like")
    public PostDTO updateLike(@RequestBody PostDTO postDTO) throws RuntimeException{
        User user;
        try{
            user = userService.getUserByUsername(postDTO.getUsername());
        } catch(RuntimeException e){
            System.err.println("User does not exist");
            throw new RuntimeException();
        }
        try{
            Algorithm algorithm = Algorithm.HMAC256(this.secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("com.kennethrdzg")
                .withClaim("username", user.getUsername())
                .build();
            verifier.verify(postDTO.getToken());
        } catch( JWTVerificationException exception){
            System.err.println("Invalid JWT");
            throw new RuntimeException();
        }
        PostLike postLike = this.postLikeService.updateLike(postDTO.getId(), user.getId(), postDTO.isLiked());
        Post post = this.postService.getPostById(postDTO.getId());
        postDTO.setUsername(this.userService.getUserById(post.getUserId()).getUsername());
        postDTO.setTimestamp(post.getTimestamp());
        postDTO.setLikes(this.postLikeService.getPostLikes(post.getId()));
        postDTO.setLiked(this.postLikeService.isLikedByUser(post.getId(), user.getId()));
        postDTO.setToken(null);
        if(postLike.isLiked() && post.getUserId() != user.getId()){
            Notification notification = new Notification(post.getId(), postDTO.getLikes(), user.getUsername() + " liked your post!");
            JSONObject jsonObject = new JSONObject(notification);
            notificationService.sendNotification(String.valueOf(post.getUserId()), jsonObject.toString());
            LOGGER.info("Sent notification to queue: " + user.getUsername());
        }
        return postDTO;
    }
}
