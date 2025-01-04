package com.shareit.user;

import com.shareit.user.dto.RequestUserDto;
import com.shareit.user.dto.ResponseUserDto;
import com.shareit.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private static final String SHARER_USER_ID = "X-Sharer-User-Id";

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseUserDto addUser(
//            @RequestHeader(name = SHARER_USER_ID) Long userId,
            @RequestBody @Valid RequestUserDto userDto
    ) {
        return userService.addUser(userDto);
    }

    @PatchMapping
    public ResponseUserDto editUser(
            @RequestHeader(name = SHARER_USER_ID) Long userId,
            @RequestBody @Valid RequestUserDto userDto
    ) {
        return userService.editUser(userId, userDto);
    }

    @DeleteMapping
    public void delUser(
            @RequestHeader(name = SHARER_USER_ID) Long userId
    ) {
        userService.deleleUser(userId);
    }

    @GetMapping("/{userId}")
    public ResponseUserDto getUser(
            @PathVariable Long userId
    ) {
        return userService.getUser(userId);
    }

    @GetMapping
    public List<ResponseUserDto> getAllUsers(
            @RequestHeader(name = SHARER_USER_ID) Long userId
    ) {
        return userService.getAllUsers();
    }


//TODO: user can send id in userDto

}
