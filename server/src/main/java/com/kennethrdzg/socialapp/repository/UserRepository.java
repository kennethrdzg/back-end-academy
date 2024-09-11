package com.kennethrdzg.socialapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kennethrdzg.socialapp.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{
}