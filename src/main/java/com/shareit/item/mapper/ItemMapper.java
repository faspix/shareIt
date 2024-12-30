package com.shareit.item.mapper;

import com.shareit.item.dto.ItemDto;
import com.shareit.item.model.Item;

public class ItemMapper {

    public Item getItemModel(ItemDto itemDto) {
        return Item.builder()
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .build();
    }

    public ItemDto getItemDto(Item item) {
        return ItemDto.builder()
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();
    }

}
