package com.kennethrdzg.socialapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kennethrdzg.socialapp.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{
}