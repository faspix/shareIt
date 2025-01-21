package com.shareit.user;

import com.shareit.security.SecurityUser;
import com.shareit.user.dto.RequestUserDto;
import com.shareit.user.dto.ResponseUserDto;
import com.shareit.user.mapper.UserMapper;
import com.shareit.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseUserDto createUser(
            @RequestBody @Valid RequestUserDto userDto
    ) {
        return userService.createUser(userDto);
    }

    @PatchMapping
    public ResponseUserDto editUser(
            @RequestBody @Valid RequestUserDto userDto,
            @AuthenticationPrincipal SecurityUser userPrincipal
            ) {
        return userService.editUser(userPrincipal.getUser(), userDto);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteUser(
            @AuthenticationPrincipal SecurityUser userPrincipal
    ) {
        return userService.deleteUser(userPrincipal.getUser());
    }

    @GetMapping("/{userId}")
    public ResponseUserDto findUser(
            @PathVariable Long userId
    ) {
        return userMapper.mapUserToResponseUserDto(userService.findUser(userId));
    }

    @GetMapping
    public List<ResponseUserDto> findAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size

    ) {
        return userService.findAllUsers(page, size);
    }

}
