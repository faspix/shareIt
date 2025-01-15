package com.shareit.user.service;

import com.shareit.user.dto.RequestUserDto;
import com.shareit.user.dto.ResponseUserDto;
import com.shareit.user.model.User;

import java.util.List;


public interface UserService {

    ResponseUserDto addUser(RequestUserDto userDto);
    void deleleUser(Long userId);
    User getUser(Long userId);
    List<ResponseUserDto> getAllUsers(int page, int size);
    ResponseUserDto editUser(Long userId, RequestUserDto userDto);

}
