package com.kennethrdzg.socialapp.rest;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kennethrdzg.socialapp.entities.Post;
import com.kennethrdzg.socialapp.service.PostService;

@RestController
@RequestMapping("/posts")
public class PostRestController {
    private PostService postService;

    @Autowired
    public PostRestController(PostService postService){
        this.postService = postService;
    }

    @GetMapping
    public List<Post> getPosts(){
        return postService.getPosts();
    }

    @GetMapping("/page/{page}")
    public List<Post> getPostsByPage(@PathVariable int page){
        if(page < 1)
            page = 1;
        return postService.getPosts(page);
    }

    @GetMapping("/{postId}")
    public Post getPostById(@PathVariable int postId){
        return postService.getPostById(postId);
    }

    @GetMapping("/user/{userId}")
    public List<Post> getPostsByUserId(@PathVariable int userId){
        return postService.getPostsByUserId(userId);
    }

    @PostMapping("/upload")
    public Post uploadPost(@RequestBody Post post){
        post.setId(0);
        post.setTimestamp(LocalDateTime.now());
        return postService.uploadPost(post);
    }
}
