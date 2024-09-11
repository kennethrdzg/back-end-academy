package com.kennethrdzg.socialapp.service;

import java.util.List;

import com.kennethrdzg.socialapp.entities.Post;

public interface PostService {
    public Post uploadPost(Post post);
    public List<Post> getPosts();
    public List<Post> getPosts(int page);
    public Post getPostById(int postId);
    public List<Post> getPostsByUserId(int userId);
}
