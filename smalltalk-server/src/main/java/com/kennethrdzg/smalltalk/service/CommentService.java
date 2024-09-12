package com.kennethrdzg.smalltalk.service;

import java.util.List;

import com.kennethrdzg.smalltalk.entities.Comment;

public interface CommentService {
    public List<Comment> getComments(int postId);
    public Comment postComment(Comment comment);
    // public void deleteComment(int commentId);
}