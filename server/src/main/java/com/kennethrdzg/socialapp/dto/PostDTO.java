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
}
