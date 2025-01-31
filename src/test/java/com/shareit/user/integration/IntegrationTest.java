package com.shareit.user.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shareit.ShareitApplication;
import com.shareit.user.dto.RequestUserDto;
import com.shareit.user.dto.ResponseUserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ShareitApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@WithMockUser(username = "test@gmail.com", password = "test", authorities = {"ADMIN", "USER"})
public class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    static final long TEST_USER_ID = 1L;

    private final RequestUserDto testRequestUser = RequestUserDto.builder()
            .name("testUser")
            .email("test@gmail.com")
            .password("testPassword")
            .build();

    private final ResponseUserDto testResponseUser = ResponseUserDto.builder()
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
        MvcResult mvcResult = mockMvc.perform(post("/users")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(testRequestUser))

                )
                .andExpect(status().is(201))
                .andReturn();
        String body = mvcResult.getResponse().getContentAsString();
        System.out.println(body);
        ResponseUserDto user = mapper.readValue(body, ResponseUserDto.class);
        Assertions.assertEquals(user.getName(), testRequestUser.getName());

    }

}
