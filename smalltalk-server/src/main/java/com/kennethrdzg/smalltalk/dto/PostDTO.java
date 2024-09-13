package com.kennethrdzg.smalltalk.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private int id;
    private String content;
    private LocalDateTime timestamp;
    private String username;
    private String token;
    private long likes;
    private boolean liked;
}
