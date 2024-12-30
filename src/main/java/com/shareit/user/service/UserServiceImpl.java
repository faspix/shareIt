package com.shareit.user.service;

import com.shareit.user.dto.UserDto;
import com.shareit.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDto addUser(Long userId, UserDto userDto) {
        return null;
    }

    @Override
    public ResponseEntity<HttpStatus> delUser(Long userId) {
        return null;
    }

    @Override
    public UserDto getUser(Long userId) {
        return null;
    }

    @Override
    public Set<UserDto> getAllUsers() {
        return Set.of();
    }





}
