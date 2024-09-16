package com.kennethrdzg.smalltalk.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kennethrdzg.smalltalk.entities.Post;
import com.kennethrdzg.smalltalk.repository.PostRepository;
import com.kennethrdzg.smalltalk.service.PostService;

@Service
public class PostServiceImpl implements PostService{
    private PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    @Override
    public Post uploadPost(Post post) throws RuntimeException{
        try{
            return this.postRepository.save(post);
        } catch(IllegalArgumentException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Post> getPosts(){
        return this.postRepository
            .findAll()
            .stream()
            .sorted(
                (p1, p2) -> p2.getTimestamp().compareTo(p1.getTimestamp())
            )
            .limit(10)
            .toList();
    }

    @Override
    public List<Post> getPosts(int page) throws RuntimeException{
        try{
            return this.postRepository.findAll()
            .stream()
            .sorted(
                (p1, p2) -> p2.getTimestamp().compareTo(p1.getTimestamp())
            )
            .skip( (page - 1) * 10)
            .limit(10)
            .toList();
        } catch(IllegalArgumentException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Post getPostById(int postId) throws RuntimeException{
        Optional<Post> result = this.postRepository.findById(postId);
        try{
            return result.orElseThrow();
        } catch(NoSuchElementException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Post> getPostsByUserId(int userId){
        return this.postRepository
            .findAll()
            .stream()
            .filter(
                (p) -> p.getUserId() == userId
            )
            .sorted(
                (p1, p2) -> p2.getTimestamp().compareTo(p1.getTimestamp())
            )
            .toList();
    }
}
