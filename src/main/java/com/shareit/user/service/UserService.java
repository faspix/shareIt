package com.shareit.user.service;

import com.shareit.user.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;


public interface UserService {

    User addUser(User user);
    ResponseEntity<HttpStatus> delUser(Long userId);
    User getUser(Long userId);
    List<User> getAllUsers();
    User editUser(Long userId, User user);

}
