package com.kennethrdzg.smalltalk.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "post_comment")
public class Comment{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentID")
    private int id;

    @Column(name = "userID")
    private int userId;

    @Column(name = "postID")
    private int postId;

    @Column(name = "comment_timestamp")
    private LocalDateTime timestamp;

    @Column(name = "comment_content")
    private String content;
}