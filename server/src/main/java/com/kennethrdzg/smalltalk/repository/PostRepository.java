package com.kennethrdzg.smalltalk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kennethrdzg.smalltalk.entities.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{
}
