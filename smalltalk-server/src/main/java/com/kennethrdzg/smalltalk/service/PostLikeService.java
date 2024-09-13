package com.kennethrdzg.smalltalk.service;

import com.kennethrdzg.smalltalk.entities.PostLike;

public interface PostLikeService {
    public long getPostLikes(int postId);
    public boolean isLikedByUser(int postId, int userId);
    public PostLike updateLike(int postId, int userId, boolean liked);
    // void likePost(int postId, int userId);
    // void unlikePost(int postId, int userId);
}