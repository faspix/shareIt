package com.shareit.user;

import com.shareit.user.model.User;
import com.shareit.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public User addUser(
//            @RequestHeader(name = SHARER_USER_ID) Long userId,
            @RequestBody @Valid User user
    ) {
        return userService.addUser(user);
    }

    @PatchMapping()
    public User editUser(
            @RequestHeader(name = SHARER_USER_ID) Long userId,
            @RequestBody @Valid User user
    ) {
        return userService.editUser(userId, user);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> delUser(
            @RequestHeader(name = SHARER_USER_ID) Long userId
    ) {
        return userService.delUser(userId);
    }

    @GetMapping("/{userId}")
    public User getUser(
            @PathVariable Long userId
    ) {
        return userService.getUser(userId);
    }

    @GetMapping
    public List<User> getAllUsers(
            @RequestHeader(name = SHARER_USER_ID) Long userId
    ) {
        return userService.getAllUsers();
    }




}
