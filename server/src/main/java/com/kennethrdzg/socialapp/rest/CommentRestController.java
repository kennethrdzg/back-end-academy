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

import com.kennethrdzg.socialapp.entities.Comment;
import com.kennethrdzg.socialapp.service.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentRestController{
    private CommentService commentService;

    @Autowired
    public CommentRestController(CommentService commentService){
        this.commentService = commentService;
    }

    @GetMapping("/{postId}")
    public List<Comment> getComments(@PathVariable int postId){
        return this.commentService.getComments(postId);
    }

    @PostMapping
    public Comment postComment(@RequestBody Comment comment){
        comment.setId(0);
        comment.setTimestamp(LocalDateTime.now());
        return this.commentService.postComment(comment);
        // System.out.println(comment);
        // return null;
    }

    // @DeleteMapping("/{commentId}")
    // public void deleteComment(@RequestBody int commentId){
    //     this.commentService.deleteComment(commentId);
    // }
}