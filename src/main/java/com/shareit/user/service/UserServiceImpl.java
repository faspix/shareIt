package com.shareit.user.service;

import com.shareit.exception.NotFoundException;
import com.shareit.exception.UserAlreadyExistException;
import com.shareit.user.dto.RequestUserDto;
import com.shareit.user.dto.ResponseUserDto;
import com.shareit.user.mapper.UserMapper;
import com.shareit.user.model.User;
import com.shareit.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.shareit.user.mapper.UserMapper.*;
import static com.shareit.utility.pageRequestMaker.makePageRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public ResponseUserDto addUser(RequestUserDto userDto) {
        try {
            return mapUserToResponseUserDto(userRepository.saveAndFlush(mapRequestUserDtoToUser(userDto)));
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
    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User with ID " + userId + " not found")
        );
    }

    @Override
    public List<ResponseUserDto> getAllUsers(int page, int size) {
        Pageable pageRequest = makePageRequest(page, size);
        Page<User> allUsers = userRepository.findAll(pageRequest);
        return allUsers
                .map(UserMapper::mapUserToResponseUserDto)
                .toList();
    }

    @Override
    @Transactional
    public ResponseUserDto editUser(Long userId, RequestUserDto userDto) {
        User existUser = getUser(userId);

        existUser.setEmail(userDto.getEmail());
        existUser.setName(userDto.getName());

        try {
            userRepository.saveAndFlush(existUser);
            return mapUserToResponseUserDto(existUser);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistException("User with email " + userDto.getEmail() + " already exist");
        }

    }



}
