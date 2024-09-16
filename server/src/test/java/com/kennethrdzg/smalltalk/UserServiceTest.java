package com.kennethrdzg.smalltalk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.kennethrdzg.smalltalk.entities.User;
import com.kennethrdzg.smalltalk.service.UserService;

class UserServiceTest{
    @Mock
    UserService userService;

    @BeforeEach
    void setUp(){
        userService = Mockito.mock(UserService.class);
    }

    @Test
    public void testGetUserById(){

        Mockito.when(userService.getUserById(1)).thenReturn(new User(1, "kenneth", "passwordhash"));

        User user = userService.getUserById(1);

        Mockito.verify(userService).getUserById(1);

        assertEquals("kenneth", user.getUsername());
    }

    @Test
    public void testUserNotFound(){
        Mockito.when(userService.getUserById(0)).thenThrow(RuntimeException.class);

        assertThrows(
            RuntimeException.class,
            () -> {
                userService.getUserById(0);
            });
    }

    @Test
    public void testRegisterUser(){
        Mockito.when(
            userService.register(
                new User(0, "kenneth", "passwordHash")
            )).thenReturn(
                new User(1, "kenneth", "passwordHash"
            ));
        User user = userService.register(new User(0, "kenneth", "passwordHash"));

        assertEquals(1, user.getId());
        assertEquals("kenneth", user.getUsername());
    }
}