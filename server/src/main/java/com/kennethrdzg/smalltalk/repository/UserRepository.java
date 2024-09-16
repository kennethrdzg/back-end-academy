package com.kennethrdzg.smalltalk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kennethrdzg.smalltalk.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{
}