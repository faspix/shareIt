package com.shareit.user.service;

import com.shareit.user.dto.RequestUserDto;
import com.shareit.user.dto.ResponseUserDto;
import com.shareit.user.mapper.UserMapper;
import com.shareit.user.model.User;
import com.shareit.user.repository.UserRepository;
import com.shareit.user.utility.UserRole;
import com.shareit.utility.PageRequestMaker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static com.shareit.utility.PageRequestMaker.makePageRequest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private final User testUser = User.builder()
            .id(1L)
            .name("testUser")
            .email("test@gmail.com")
            .password("testPassword")
            .userRole(UserRole.USER)
            .build();

    private final RequestUserDto testRequestUser = RequestUserDto.builder()
            .name(testUser.getName())
            .email(testUser.getEmail())
            .password(testUser.getPassword())
            .build();

    private final ResponseUserDto testResponseUser = ResponseUserDto.builder()
            .id(testUser.getId())
            .name(testUser.getName())
            .email(testUser.getEmail())
            .build();

    private UserService userService;


    @BeforeEach
    public void init() {
        userService = new UserServiceImpl(userRepository, new UserMapper(new BCryptPasswordEncoder()));
    }

    @Test
    void createUserTest() {
        when(userRepository.saveAndFlush(any()))
                .thenReturn(testUser);

        var savedUser = userService.createUser(testRequestUser);

        assertEquals(savedUser.getId(), testUser.getId());
        assertEquals(savedUser.getEmail(), testUser.getEmail());
        assertEquals(savedUser.getName(), testUser.getName());
    }

    @Test
    void findUserTest() {
        when(userRepository.findById(any()))
                .thenReturn(Optional.ofNullable(testUser));

        var getUserResult = userService.findUser(anyLong());

        assertEquals(testUser.getId(), getUserResult.getId());
        assertEquals(testUser.getName(), getUserResult.getName());
        assertEquals(testUser.getEmail(), getUserResult.getEmail());

    }

    @Test
    void editUserTest() {
        when(userRepository.saveAndFlush(any()))
                .thenReturn(testUser);
        RequestUserDto updatedUser = new RequestUserDto("Updated name",
                testUser.getEmail(),
                testUser.getPassword());

        var user = userService.editUser(testUser, updatedUser);

        assertEquals(user.getName(), updatedUser.getName());
        assertEquals(user.getEmail(), updatedUser.getEmail());

    }


}
