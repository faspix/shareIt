package com.shareit.item.mapper;

import com.shareit.item.dto.*;
import com.shareit.item.model.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemMapper {

    private final CommentMapper commentMapper;

    public Item mapRequestItemDtoToItem(RequestItemDto itemDto) {
        return Item.builder()
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .build();
    }

    public ResponseItemDto mapItemToResponseItemDto(Item item) {
        return ResponseItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .comments((item.getComments()
                        .stream()
                        .map(commentMapper::mapCommentToResponseCommentDto)
                        .toList()))
                .build();
    }

    public OwnerResponseItemDto mapItemToOwnerResponseItemDto(Item item) {
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


    public static ResponseSearchItemDto mapItemToResponseSearchItemDto(Item item) {
        return ResponseSearchItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .build();
    }

}
