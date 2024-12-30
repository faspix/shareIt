package com.shareit.user;

import com.shareit.user.dto.UserDto;
import com.shareit.user.model.User;
import com.shareit.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private static final String SHARER_USER_ID = "X-Sharer-User-Id";

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDto addUser(
            @RequestHeader(name = SHARER_USER_ID) Long userId,
            @RequestBody UserDto userDto
    ) {
        return userService.addUser(userId, userDto);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> delUser(
            @RequestHeader(name = SHARER_USER_ID) Long userId
    ) {
        return userService.delUser(userId);
    }

    @GetMapping("/userId")
    public UserDto getUser(
            @RequestParam Long userId
    ) {
        return userService.getUser(userId);
    }

    @GetMapping
    public Set<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }




}
