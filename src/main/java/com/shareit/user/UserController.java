package com.shareit.user;

import com.shareit.user.dto.RequestUserDto;
import com.shareit.user.dto.ResponseUserDto;
import com.shareit.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.shareit.user.mapper.UserMapper.mapUserToResponseUserDto;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private static final String SHARER_USER_ID = "X-Sharer-User-Id";

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseUserDto createUser(
            @RequestBody @Valid RequestUserDto userDto
    ) {
        return userService.createUser(userDto);
    }

    @PatchMapping
    public ResponseUserDto editUser(
            @RequestHeader(name = SHARER_USER_ID) Long userId,
            @RequestBody @Valid RequestUserDto userDto
    ) {
        return userService.editUser(userId, userDto);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteUser(
            @RequestHeader(name = SHARER_USER_ID) Long userId
    ) {
        return userService.deleteUser(userId);
    }

    @GetMapping("/{userId}")
    public ResponseUserDto findUser(
            @PathVariable Long userId
    ) {
        return mapUserToResponseUserDto(userService.findUser(userId));
    }

    @GetMapping
    public List<ResponseUserDto> findAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size

    ) {
        return userService.findAllUsers(page, size);
    }

}
