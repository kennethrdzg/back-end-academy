package com.kennethrdzg.smalltalk.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification{
    public int postId;
    public long likes;
    public String message;
}