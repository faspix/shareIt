package com.shareit.user.service;

import com.shareit.user.dto.RequestUserDto;
import com.shareit.user.mapper.UserMapper;
import com.shareit.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static com.shareit.user.utils.UserUtils.REQUEST_USER_DTO_TEST;
import static com.shareit.user.utils.UserUtils.USER_TEST;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;


    @BeforeEach
    public void init() {
        userService = new UserServiceImpl(userRepository, new UserMapper(new BCryptPasswordEncoder()));
    }

    @Test
    void createUserTest() {
        when(userRepository.saveAndFlush(any()))
                .thenReturn(USER_TEST);

        var savedUser = userService.createUser(REQUEST_USER_DTO_TEST);

        assertEquals(savedUser.getId(), USER_TEST.getId());
        assertEquals(savedUser.getEmail(), USER_TEST.getEmail());
        assertEquals(savedUser.getName(), USER_TEST.getName());
    }

    @Test
    void findUserTest() {
        when(userRepository.findById(any()))
                .thenReturn(Optional.ofNullable(USER_TEST));

        var getUserResult = userService.findUser(anyLong());

        assertEquals(USER_TEST.getId(), getUserResult.getId());
        assertEquals(USER_TEST.getName(), getUserResult.getName());
        assertEquals(USER_TEST.getEmail(), getUserResult.getEmail());

    }

    @Test
    void editUserTest() {
        when(userRepository.saveAndFlush(any()))
                .thenReturn(USER_TEST);
        RequestUserDto updatedUser = new RequestUserDto("Updated name",
                USER_TEST.getEmail(),
                USER_TEST.getPassword());

        var user = userService.editUser(USER_TEST, updatedUser);

        assertEquals(user.getName(), updatedUser.getName());
        assertEquals(user.getEmail(), updatedUser.getEmail());

    }


}
