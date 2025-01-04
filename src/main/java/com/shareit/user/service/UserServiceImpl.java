package com.shareit.user.service;

import com.shareit.exception.UserAlreadyExistException;
import com.shareit.exception.UserNotFoundException;
import com.shareit.user.dto.RequestUserDto;
import com.shareit.user.dto.ResponseUserDto;
import com.shareit.user.mapper.UserMapper;
import com.shareit.user.model.User;
import com.shareit.user.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.shareit.user.mapper.UserMapper.*;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    @Transactional
    public ResponseUserDto addUser(RequestUserDto userDto) {
        try {
            var resultUser = mapUserToResponseUserDto(userRepository.save(mapRequestUserDtoToUser(userDto)));
            userRepository.flush();
            return resultUser;
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistException("User with email " + userDto.getEmail() + " already exist");
        }
    }

    @Override
    @Transactional
    public void deleleUser(Long userId) {
        userRepository.deleteUserById(userId); // TODO: doesn't work on users with items
    }

    @Override
    public ResponseUserDto getUser(Long userId) {
        return mapUserToResponseUserDto(
                userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        ));
    }

    @Override
    public List<ResponseUserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::mapUserToResponseUserDto)
                .toList();
    }

    @Override
    @Transactional
    public ResponseUserDto editUser(Long userId, RequestUserDto userDto) {

        User existUser = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );


        existUser.setEmail(userDto.getEmail());
        existUser.setName(userDto.getName());

        try {
            userRepository.save(existUser);
            userRepository.flush();
            return mapUserToResponseUserDto(existUser);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistException("User with email " + userDto.getEmail() + " already exist");
        }

    }



}
