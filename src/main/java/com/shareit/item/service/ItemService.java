package com.shareit.item.service;

import com.shareit.item.dto.*;

import java.util.List;

public interface ItemService {

    ResponseItemDto addItem(Long userId, RequestItemDto item);

    ResponseItemDto editItem(Long userId, Long itemId, RequestItemDto itemDto);

    ResponseItemDto getItem(Long itemId);

    List<OwnerResponseItemDto> getAllUsersItems(Long userId);

    List<ResponseItemDto> findItems(String text);

    void deleteItem(Long userId, Long itemId);

    ResponseCommentDto addComment(Long userId, Long itemId, RequestCommentDto commentDto);

}

