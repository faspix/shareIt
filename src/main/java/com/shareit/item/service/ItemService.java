package com.shareit.item.service;

import com.shareit.item.dto.ItemDto;

import java.util.Set;

public interface ItemService {

    ItemDto addItem(Long userId, ItemDto itemDto);

    ItemDto editItem(Long userId, Long itemId);

    ItemDto getItem(Long userId, Long itemId);

    Set<ItemDto> getAllUsersItems(Long userId);

    Set<ItemDto> findItems(String text);

}

