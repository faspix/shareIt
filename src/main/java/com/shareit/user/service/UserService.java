package com.shareit.user.service;

import com.shareit.user.dto.UserDto;
import com.shareit.user.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;


public interface UserService {

    UserDto addUser(Long userId, UserDto userDto);
    ResponseEntity<HttpStatus> delUser(Long userId);
    UserDto getUser(Long userId);
    Set<UserDto> getAllUsers();

}
