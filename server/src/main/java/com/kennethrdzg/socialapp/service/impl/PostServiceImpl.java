package com.kennethrdzg.socialapp.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kennethrdzg.socialapp.entities.Post;
import com.kennethrdzg.socialapp.repository.PostRepository;
import com.kennethrdzg.socialapp.service.PostService;

@Service
public class PostServiceImpl implements PostService{
    private PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    @Override
    public Post uploadPost(Post post){
        return this.postRepository.save(post);
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
    public List<Post> getPosts(int page){
        return this.postRepository.findAll()
            .stream()
            .sorted(
                (p1, p2) -> p2.getTimestamp().compareTo(p1.getTimestamp())
            )
            .skip( (page - 1) * 10)
            .limit(10)
            .toList();
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

    @Override
    public Post getPostById(int postId){
        Optional<Post> result = this.postRepository.findById(postId);
        return result.isPresent() ? result.get(): null;
    }
}
