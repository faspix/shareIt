package com.shareit.user;

import com.shareit.user.dto.RequestUserDto;
import com.shareit.user.dto.ResponseUserDto;
import com.shareit.user.service.UserService;
import jakarta.validation.Valid;
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
    public ResponseUserDto addUser(
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
        return mapUserToResponseUserDto(userService.getUser(userId));
    }

    @GetMapping
    public List<ResponseUserDto> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size

    ) {
        return userService.getAllUsers(page, size);
    }

}
