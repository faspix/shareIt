package com.shareit.item;

import com.shareit.item.dto.*;
import com.shareit.item.mapper.ItemMapper;
import com.shareit.item.service.ItemService;
import com.shareit.security.SecurityUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    private final ItemMapper itemMapper;

    @PostMapping
    public ResponseItemDtoNoComments addItem(
            @AuthenticationPrincipal SecurityUser userPrincipal,
            @RequestBody @Valid RequestItemDto itemDto
    ) {
        return itemService.addItem(userPrincipal.getUser(), itemDto);
    }

    @PatchMapping("/{itemId}")
    public ResponseItemDto editItem(
            @AuthenticationPrincipal SecurityUser userPrincipal,
            @PathVariable Long itemId,
            @RequestBody @Valid RequestItemDto itemDto
    ) {
        return itemService.editItem(userPrincipal.getUser(), itemId, itemDto);
    }

    @GetMapping("/{itemId}")
    public ResponseItemDto getItem(
            @PathVariable Long itemId
    ) {
        return itemMapper.mapItemToResponseItemDto(itemService.getItem(itemId));
    }

    @GetMapping
    public List<OwnerResponseItemDto> getAllUsersItems(
            @AuthenticationPrincipal SecurityUser userPrincipal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size
    ) {
        return itemService.getAllUsersItems(userPrincipal.getUser(), page, size);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<HttpStatus> deleteItem(
            @AuthenticationPrincipal SecurityUser userPrincipal,
            @PathVariable Long itemId
    ) {
        return itemService.deleteItem(userPrincipal.getUser(), itemId);

    }

    @GetMapping("/search")
    public List<ResponseSearchItemDto> findItems(
           @RequestParam(required = false) String query,
           @RequestParam(defaultValue = "0") int page,
           @RequestParam(defaultValue = "30") int size
    ) {
        return itemService.findItems(query, page, size);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseCommentDto addComment(
            @AuthenticationPrincipal SecurityUser userPrincipal,
            @PathVariable Long itemId,
            @RequestBody @Valid RequestCommentDto commentDto
    ) {
        return itemService.addComment(userPrincipal.getUser(), itemId, commentDto);
    }

}
