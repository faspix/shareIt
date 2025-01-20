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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.shareit.utility.pageRequestMaker.makePageRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    @Transactional
    public ResponseUserDto createUser(RequestUserDto userDto) {
        try {
            return userMapper.mapUserToResponseUserDto(
                    userRepository.saveAndFlush(
                            userMapper.mapRequestUserDtoToUser(userDto)
                    )
            );
        } catch (DataIntegrityViolationException e) {
            System.out.println(e);
            throw new UserAlreadyExistException("User with email " + userDto.getEmail() + " already exist");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> deleteUser(User user) {
        userRepository.delete(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Override
    public User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User with ID " + userId + " not found")
        );
    }

    @Override
    public List<ResponseUserDto> findAllUsers(int page, int size) {
        Pageable pageRequest = makePageRequest(page, size);
        Page<User> allUsers = userRepository.findAll(pageRequest);
        return allUsers
                .map(userMapper::mapUserToResponseUserDto)
                .toList();
    }

    @Override
    @Transactional
    public ResponseUserDto editUser(User existUser, RequestUserDto userDto) {
//        User existUser = findUser(userId);

        existUser.setEmail(userDto.getEmail());
        existUser.setName(userDto.getName());

        try {
            userRepository.saveAndFlush(existUser);
            return userMapper.mapUserToResponseUserDto(existUser);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistException("User with email " + userDto.getEmail() + " already exist");
        }

    }



}
