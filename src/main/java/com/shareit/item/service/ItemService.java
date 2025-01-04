package com.shareit.item.service;

import com.shareit.item.dto.RequestItemDto;
import com.shareit.item.dto.ResponseItemDto;

import java.util.List;

public interface ItemService {

    ResponseItemDto addItem(Long userId, RequestItemDto item);

    ResponseItemDto editItem(Long userId, Long itemId, RequestItemDto itemDto);

    ResponseItemDto getItem(Long itemId);

    List<ResponseItemDto> getAllUsersItems(Long userId);

    List<ResponseItemDto> findItems(String text);

    void deleteItem(Long userId, Long itemId);
}

