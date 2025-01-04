package com.shareit.item.mapper;

import com.shareit.item.dto.RequestItemDto;
import com.shareit.item.dto.ResponseItemDto;
import com.shareit.item.model.Item;

public class ItemMapper {


    public static Item mapRequestItemDtoToItem(RequestItemDto itemDto) {
        return Item.builder()
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .build();
    }

    public static ResponseItemDto mapItemToResponseItemDto(Item item) {
        return ResponseItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();
    }

    public static Item mapResponseItemDtoToItem(ResponseItemDto itemDto) {
        return Item.builder()
                .id(itemDto.getId())
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .build();
    }

}
