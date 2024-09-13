package com.kennethrdzg.smalltalk.service;

import java.util.List;

import com.kennethrdzg.smalltalk.dto.PostDTO;
import com.kennethrdzg.smalltalk.entities.Post;

public interface PostService {
    public PostDTO uploadPost(Post post) throws RuntimeException;
    public List<PostDTO> getPosts();
    public List<PostDTO> getPosts(int page) throws RuntimeException;
    public PostDTO getPostById(int postId) throws RuntimeException;
    public List<PostDTO> getPostsByUserId(int userId);
}
