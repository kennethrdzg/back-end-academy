package com.kennethrdzg.smalltalk.service;

import java.util.List;

import com.kennethrdzg.smalltalk.entities.Post;

public interface PostService {
    public Post uploadPost(Post post) throws RuntimeException;
    public List<Post> getPosts();
    public List<Post> getPosts(int page) throws RuntimeException;
    public Post getPostById(int postId) throws RuntimeException;
    public List<Post> getPostsByUserId(int userId);
}
