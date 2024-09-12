package com.kennethrdzg.socialapp.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private String content;
    private LocalDateTime timestamp;
    private int userId;
    private String token;
    /*public class Post{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postID")
    private int id;
    
    @Column(name = "post_content")
    private String content;

    @Column(name = "post_timestamp")
    private LocalDateTime timestamp;

    @Column(name = "userID")
    private int userId;
} */
}
