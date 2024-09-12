package com.kennethrdzg.socialapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserToken{
    private int id;
    private String username;
    private String token;
}