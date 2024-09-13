package com.kennethrdzg.smalltalk.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kennethrdzg.smalltalk.entities.PostLike;
import com.kennethrdzg.smalltalk.repository.PostLikeRepository;
import com.kennethrdzg.smalltalk.service.PostLikeService;

@Service
public class PostLikeServiceImpl implements PostLikeService{
    private PostLikeRepository postLikeRepository;

    @Autowired
    public PostLikeServiceImpl(PostLikeRepository postLikeRepository){
        this.postLikeRepository = postLikeRepository;
    }

    @Override
    public long getPostLikes(int postId){
        return this.postLikeRepository
            .findAll()
            .stream()
            .filter(
                (p) -> (p.getPostId() == postId && p.isLiked())
            ).count();
    }

    @Override
    public boolean isLikedByUser(int postId, int userId){
        Optional<PostLike> result = this.postLikeRepository
            .findAll()
            .stream()
            .filter(
                (like) -> like.getPostId() == postId && like.getUserId() == userId
            ).findAny();
        if(result.isPresent()){
            return result.orElseThrow().isLiked();
        }
        return false;
    }
}