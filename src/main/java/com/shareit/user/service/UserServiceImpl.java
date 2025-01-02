package com.shareit.user.service;

import com.shareit.exception.UserAlreadyExistException;
import com.shareit.exception.UserNotFoundException;
import com.shareit.exception.UserValidationException;
import com.shareit.user.model.User;
import com.shareit.user.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User addUser(User user) {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistException("User with email " + user.getEmail() + " already exist");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> delUser(Long userId) {
        userRepository.deleteUserById(userId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Override
    public User getUser(Long userId) {
        return (userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        ));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .toList();
    }

    @Override
    @Transactional
    public User editUser(Long userId, User user) {

        userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );

        user.setName(user.getName());
        user.setEmail(user.getEmail());

        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistException("User with email " + user.getEmail() + " already exist");
        }
    }



}
