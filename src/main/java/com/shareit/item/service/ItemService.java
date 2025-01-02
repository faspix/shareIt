package com.shareit.item.service;

import com.shareit.item.dto.ItemDto;
import com.shareit.item.model.Item;

import java.util.List;
import java.util.Set;

public interface ItemService {

    ItemDto addItem(Long userId, ItemDto item);

    ItemDto editItem(Long userId, Long itemId, ItemDto itemDto);

    ItemDto getItem(Long userId, Long itemId);

    List<ItemDto> getAllUsersItems(Long userId);

    List<ItemDto> findItems(String text);

}

