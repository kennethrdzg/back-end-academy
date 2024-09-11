package com.kennethrdzg.socialapp.service;

import java.util.List;

import com.kennethrdzg.socialapp.entities.Comment;

public interface CommentService {
    public List<Comment> getComments(int postId);
    public Comment postComment(Comment comment);
    // public void deleteComment(int commentId);
}