package com.shareit.user.service;

import com.shareit.user.UserService;
import com.shareit.user.UserServiceImpl;
import com.shareit.user.dto.RequestUserDto;
import com.shareit.user.dto.ResponseUserDto;
import com.shareit.user.model.User;
import com.shareit.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    private UserService userService;
    private RequestUserDto requestUserDto;
    private ResponseUserDto responseUserDto;
    private User user;

    @BeforeEach
    void init() {
        userService = new UserServiceImpl(userRepository);
        user = new User(1L, "test2Name", "testEmail@mail.com");
        responseUserDto = new ResponseUserDto(1L, "testName", "testEmail@mail.com");//mapUserToResponseUserDto(user);
        requestUserDto = new RequestUserDto("testName", "testEmail@mail.com");
    }

    @Test
    void addUserTest() {
        when(userRepository.save(any()))
                .thenReturn(user);

        var savedUser = userService.addUser(requestUserDto);

        assertEquals(savedUser.getId(), user.getId());
        assertEquals(savedUser.getEmail(), user.getEmail());
        assertEquals(savedUser.getName(), user.getName());


    }

    @Test
    void getUserTest() {
        when(userRepository.findById(any()))
                .thenReturn(Optional.ofNullable(user));

        var getUserResult = userService.getUser(anyLong());

        assertEquals(user.getId(), getUserResult.getId());
        assertEquals(user.getName(), getUserResult.getName());
        assertEquals(user.getEmail(), getUserResult.getEmail());

    }


}
