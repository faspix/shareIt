package com.shareit.user.service;

import com.shareit.user.dto.RequestUserDto;
import com.shareit.user.dto.ResponseUserDto;
import com.shareit.user.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface UserService {

    ResponseUserDto createUser(RequestUserDto userDto);
    ResponseEntity<HttpStatus> deleteUser(Long userId);
    User findUser(Long userId);
    List<ResponseUserDto> findAllUsers(int page, int size);
    ResponseUserDto editUser(Long userId, RequestUserDto userDto);

}
