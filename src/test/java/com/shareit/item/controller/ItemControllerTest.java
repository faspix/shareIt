package com.shareit.item.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shareit.item.ItemController;
import com.shareit.item.dto.ResponseSearchItemDto;
import com.shareit.item.mapper.ItemMapper;
import com.shareit.item.model.Item;
import com.shareit.item.repository.ItemRepository;
import com.shareit.item.service.ItemService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.shareit.item.utils.ItemUtils.ITEM_TEST;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.when;

@WebMvcTest(controllers = ItemController.class)
@WithMockUser(username = "test@gmail.com", password = "test", authorities = {"ADMIN", "USER"})
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ItemService itemService;

    @MockitoBean
    private ItemMapper itemMapper;

    @MockitoBean
    private ItemRepository itemRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getItemTest() throws Exception {
        mockMvc.perform(get("/items/1"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void findItemsTest() throws Exception {

        ResponseSearchItemDto responseSearchItemDto =
                new ResponseSearchItemDto(1L, "name", "desc");

        when(itemService.findItems(any(), anyInt(), anyInt()))
                .thenReturn((List.of(responseSearchItemDto, responseSearchItemDto)));

        MvcResult mvcResult = mockMvc.perform(get("/items/search")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().is2xxSuccessful())
                .andReturn();
        String body = mvcResult.getResponse().getContentAsString();
        List<ResponseSearchItemDto> response = objectMapper.readValue(
                body, new TypeReference<>() {} );

        assertEquals(response.getFirst().getId(), responseSearchItemDto.getId());
        assertEquals(response.getFirst().getName(), responseSearchItemDto.getName());
        assertEquals(response.getFirst().getDescription(), responseSearchItemDto.getDescription());
        assertEquals(response.getLast().getId(), responseSearchItemDto.getId());
        assertEquals(response.getLast().getName(), responseSearchItemDto.getName());
        assertEquals(response.getLast().getDescription(), responseSearchItemDto.getDescription());
    }

}
