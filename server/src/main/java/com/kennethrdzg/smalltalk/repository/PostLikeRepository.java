package com.kennethrdzg.smalltalk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kennethrdzg.smalltalk.entities.PostLike;

public interface PostLikeRepository extends JpaRepository<PostLike, Integer>{
}