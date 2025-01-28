package com.shareit.user.mapper;

import com.shareit.user.dto.RequestUserDto;
import com.shareit.user.dto.ResponseUserDto;
import com.shareit.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User mapRequestUserDtoToUser(RequestUserDto userDto) {
        return User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(Optional.ofNullable(userDto.getPassword())
                                .map(passwordEncoder::encode)
                                .orElse(null)
                        )
                .build();
    }

    public ResponseUserDto mapUserToResponseUserDto(User user) {
        return ResponseUserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

}
