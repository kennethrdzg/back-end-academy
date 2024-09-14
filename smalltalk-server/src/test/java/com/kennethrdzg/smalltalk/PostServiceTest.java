package com.kennethrdzg.smalltalk;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.kennethrdzg.smalltalk.dto.PostDTO;
import com.kennethrdzg.smalltalk.entities.Post;
import com.kennethrdzg.smalltalk.entities.User;
import com.kennethrdzg.smalltalk.rest.PostRestController;
import com.kennethrdzg.smalltalk.service.PostLikeService;
import com.kennethrdzg.smalltalk.service.PostService;
import com.kennethrdzg.smalltalk.service.UserService;

class PostServiceTest{
    @Mock
    PostService postService;
    @Mock
    UserService userService;
    @Mock
    PostLikeService postLikeService;

    @BeforeEach
    public void setUp(){
        postService = Mockito.mock(PostService.class);
        userService = Mockito.mock(UserService.class);
        postLikeService = Mockito.mock(PostLikeService.class);
    }

    @Test
    public void testGetPostById(){
        Mockito.when(postService.getPostById(1)).thenReturn(new Post(1, "Hello, world!", null, 2));
        Mockito.when(userService.getUserById(2)).thenReturn(new User(2, "kenneth", "passwordHash"));
        Mockito.when(userService.getUserByUsername("kenneth")).thenReturn(new User(2, "kenneth", "passwordHash"));
        Mockito.when(postLikeService.getPostLikes(1)).thenReturn(5L);
        Mockito.when(postLikeService.isLikedByUser(1, 2)).thenReturn(true);

        PostRestController postController = new PostRestController(postService, userService, postLikeService);
        
        PostDTO postDTO = postController.getPostById(1);
        assertEquals(1, postDTO.getId());
        assertEquals("Hello, world!", postDTO.getContent());
        assertEquals("kenneth", postDTO.getUsername());
        assertEquals(5L, postDTO.getLikes());
    }
}