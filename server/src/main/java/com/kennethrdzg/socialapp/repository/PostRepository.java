package com.kennethrdzg.socialapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kennethrdzg.socialapp.entities.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{
}
