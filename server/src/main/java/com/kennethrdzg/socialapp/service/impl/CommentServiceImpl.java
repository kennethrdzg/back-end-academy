package com.kennethrdzg.socialapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kennethrdzg.socialapp.entities.Comment;
import com.kennethrdzg.socialapp.repository.CommentRepository;
import com.kennethrdzg.socialapp.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
    private CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> getComments(int postId){
        return this.commentRepository.findAll()
            .stream()
            .filter(
                (c) -> c.getPostId() == postId
            )
            .toList();
    }

    @Override
    public Comment postComment(Comment comment){
        return this.commentRepository.save(comment);
    }

    // @Override
    // public void deleteComment(int commentId){
    //     this.commentRepository.deleteById(commentId);
    // }
}