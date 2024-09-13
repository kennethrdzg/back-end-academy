package com.kennethrdzg.smalltalk.service;

public interface PostLikeService {
    long getPostLikes(int postId);
    boolean isLikedByUser(int postId, int userId);
    // void likePost(int postId, int userId);
    // void unlikePost(int postId, int userId);
}