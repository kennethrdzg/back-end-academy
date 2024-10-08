package com.kennethrdzg.smalltalk.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserToken{
    private int userId;
    private String username;
    private String token;
}