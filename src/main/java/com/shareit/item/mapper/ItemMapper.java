package com.shareit.item.mapper;

import com.shareit.item.dto.OwnerResponseItemDto;
import com.shareit.item.dto.RequestItemDto;
import com.shareit.item.dto.ResponseItemDto;
import com.shareit.item.dto.ResponseItemDtoNoComments;
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
                .comments((item.getComments()
                        .stream()
                        .map(CommentMapper::mapCommentToResponseCommentDto)
                        .toList()))
                .build();
    }

    public static OwnerResponseItemDto mapItemToOwnerResponseItemDto(Item item) {
        return OwnerResponseItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();
    }

    public static Item mapOwnerResponseItemDtoToItem(OwnerResponseItemDto itemDto) {
        return Item.builder()
                .id(itemDto.getId())
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .build();
    }

    public static ResponseItemDtoNoComments mapItemToResponseItemDtoNoComments(Item item) {
        return ResponseItemDtoNoComments.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();
    }

}
