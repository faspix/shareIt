package com.shareit.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shareit.user.UserController;
import com.shareit.user.dto.RequestUserDto;
import com.shareit.user.dto.ResponseUserDto;
import com.shareit.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = UserController.class)
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    UserService userService;

    static final long TEST_USER_ID = 1L;

    RequestUserDto testRequestUser = RequestUserDto.builder()
            .name("testUser")
            .email("test@gmail.com")
            .build();

    ResponseUserDto testResponseUser = ResponseUserDto.builder()
            .id(TEST_USER_ID)
            .name(testRequestUser.getName())
            .email(testRequestUser.getEmail())
            .build();


    @Test
    void findAllUsersTest() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void createUserTest() throws Exception {
        when(userService.createUser(any(RequestUserDto.class)))
                .thenReturn(testResponseUser);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(testRequestUser))

        )
                .andExpectAll(
                        status().is2xxSuccessful(),
                        jsonPath("$.id", is(testResponseUser.getId()), Long.class),
                        jsonPath("$.name", is(testResponseUser.getName())),
                        jsonPath("$.email", is(testResponseUser.getEmail()))
                );
    }

}
