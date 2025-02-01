package com.shareit.user.utils;

import com.shareit.user.dto.RequestUserDto;
import com.shareit.user.dto.ResponseUserDto;
import com.shareit.user.model.User;
import com.shareit.user.utility.UserRole;

public class UserUtils {

    public static final long TEST_USER_ID = 1L;

    public static final User USER_TEST = User.builder()
            .id(TEST_USER_ID)
            .name("testUser")
            .email("test@gmail.com")
            .password("testPassword")
            .userRole(UserRole.USER)
            .build();

    public static final RequestUserDto REQUEST_USER_DTO_TEST = RequestUserDto.builder()
            .name(USER_TEST.getName())
            .email(USER_TEST.getEmail())
            .password(USER_TEST.getPassword())
            .build();

    public static final ResponseUserDto RESPONSE_USER_DTO_TEST = ResponseUserDto.builder()
            .id(USER_TEST.getId())
            .name(USER_TEST.getName())
            .email(USER_TEST.getEmail())
            .build();

}
