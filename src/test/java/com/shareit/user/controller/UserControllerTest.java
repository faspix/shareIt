package com.shareit.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shareit.user.UserController;
import com.shareit.user.dto.RequestUserDto;
import com.shareit.user.mapper.UserMapper;
import com.shareit.user.repository.UserRepository;
import com.shareit.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static com.shareit.user.utils.UserUtils.REQUEST_USER_DTO_TEST;
import static com.shareit.user.utils.UserUtils.RESPONSE_USER_DTO_TEST;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@WithMockUser(username = "test@gmail.com", password = "test", authorities = {"ADMIN", "USER"})
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private UserMapper userMapper;


    @Test
    void findAllUsersTest() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void createUserTest() throws Exception {
        when(userService.createUser(any(RequestUserDto.class)))
                .thenReturn(RESPONSE_USER_DTO_TEST);

        mockMvc.perform(post("/users")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(REQUEST_USER_DTO_TEST))

        ).andExpectAll(
                status().is2xxSuccessful(),
                jsonPath("$.id", is(RESPONSE_USER_DTO_TEST.getId()), Long.class),
                jsonPath("$.name", is(RESPONSE_USER_DTO_TEST.getName())),
                jsonPath("$.email", is(RESPONSE_USER_DTO_TEST.getEmail()))
        );
    }

}
