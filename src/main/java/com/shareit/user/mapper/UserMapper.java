package com.shareit.user.mapper;

import com.shareit.user.dto.UserDto;
import com.shareit.user.model.User;

public class UserMapper {

    public User getUserModel(UserDto userDto) {
        return User.builder()
                .email(userDto.getEmail())
                .name(userDto.getName())
                .build();
    }

    public UserDto getUserDto(User user) {
        return UserDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

}
