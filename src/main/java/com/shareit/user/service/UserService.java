package com.shareit.user.service;

import com.shareit.user.dto.RequestUserDto;
import com.shareit.user.dto.ResponseUserDto;

import java.util.List;


public interface UserService {

    ResponseUserDto addUser(RequestUserDto userDto);
    void deleleUser(Long userId);
    ResponseUserDto getUser(Long userId);
    List<ResponseUserDto> getAllUsers();
    ResponseUserDto editUser(Long userId, RequestUserDto userDto);

}
