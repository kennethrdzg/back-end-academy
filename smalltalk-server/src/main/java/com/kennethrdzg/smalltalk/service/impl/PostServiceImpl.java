package com.kennethrdzg.smalltalk.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kennethrdzg.smalltalk.dto.PostDTO;
import com.kennethrdzg.smalltalk.entities.Post;
import com.kennethrdzg.smalltalk.repository.PostRepository;
import com.kennethrdzg.smalltalk.repository.UserRepository;
import com.kennethrdzg.smalltalk.service.PostService;

@Service
public class PostServiceImpl implements PostService{
    private PostRepository postRepository;
    private UserRepository userRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository){
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PostDTO uploadPost(Post post) throws RuntimeException{
        try{
            post = this.postRepository.save(post);
            return new PostDTO(post.getId(), post.getContent(), post.getTimestamp(), "", "");
        } catch(IllegalArgumentException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<PostDTO> getPosts(){
        return this.postRepository
            .findAll()
            .stream()
            .sorted(
                (p1, p2) -> p2.getTimestamp().compareTo(p1.getTimestamp())
            )
            .limit(10)
            .map(
                (post) -> new PostDTO(
                    post.getId(),
                    post.getContent(),
                    post.getTimestamp(),
                    this.userRepository.findById(post.getUserId()).orElseThrow().getUsername(),
                    "")
            )
            .toList();
    }

    @Override
    public List<PostDTO> getPosts(int page) throws RuntimeException{
        try{
            return this.postRepository.findAll()
            .stream()
            .sorted(
                (p1, p2) -> p2.getTimestamp().compareTo(p1.getTimestamp())
            )
            .skip( (page - 1) * 10)
            .limit(10)
            .map(
                (post) -> new PostDTO(
                    post.getId(),
                    post.getContent(),
                    post.getTimestamp(),
                    this.userRepository.findById(post.getUserId()).orElseThrow().getUsername(), 
                    "")
            )
            .toList();
        } catch(IllegalArgumentException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public PostDTO getPostById(int postId) throws RuntimeException{
        Optional<Post> result = this.postRepository.findById(postId);
        try{
            Post post = result.orElseThrow();
            return new PostDTO(
                post.getId(),
                post.getContent(),
                post.getTimestamp(),
                userRepository.findById(post.getUserId()).orElseThrow().getUsername(),
                "");
        } catch(NoSuchElementException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<PostDTO> getPostsByUserId(int userId){
        return this.postRepository
            .findAll()
            .stream()
            .filter(
                (p) -> p.getUserId() == userId
            )
            .sorted(
                (p1, p2) -> p2.getTimestamp().compareTo(p1.getTimestamp())
            )
            .map(
                (post) -> new PostDTO(
                    post.getId(),
                    post.getContent(),
                    post.getTimestamp(),
                    this.userRepository
                        .findById(post.getUserId())
                        .orElseThrow()
                        .getUsername(),
                    "")
            )
            .toList();
    }
}
