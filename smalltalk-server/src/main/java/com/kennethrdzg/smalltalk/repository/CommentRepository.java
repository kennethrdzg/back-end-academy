package com.kennethrdzg.smalltalk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kennethrdzg.smalltalk.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{
}