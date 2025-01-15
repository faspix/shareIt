package com.shareit.item;

import com.shareit.item.dto.*;
import com.shareit.item.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.shareit.item.mapper.ItemMapper.mapItemToResponseItemDto;


@RestController
@RequestMapping("/items")
public class ItemController {

    private static final String SHARER_USER_ID = "X-Sharer-User-Id";

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseItemDtoNoComments addItem(
            @RequestHeader(name = SHARER_USER_ID) Long userId,
            @RequestBody @Valid RequestItemDto itemDto
    ) {
        return itemService.addItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ResponseItemDto editItem(
            @RequestHeader(name = SHARER_USER_ID) Long userId,
            @PathVariable Long itemId,
            @RequestBody @Valid RequestItemDto itemDto
    ) {
        return itemService.editItem(userId, itemId, itemDto);
    }

    @GetMapping("/{itemId}")
    public ResponseItemDto getItem(
            @PathVariable Long itemId
    ) {
        return mapItemToResponseItemDto(itemService.getItem(itemId));
    }

    @GetMapping
    public List<OwnerResponseItemDto> getAllUsersItems(
            @RequestHeader(name = SHARER_USER_ID) Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size
    ) {
        return itemService.getAllUsersItems(userId, page, size);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem( // ResponseEntity<HttpStatus>
            @RequestHeader(name = SHARER_USER_ID) Long userId,
            @PathVariable Long itemId
    ) {
        itemService.deleteItem(userId, itemId); // ResponseEntity.ok(HttpStatus.OK)
    }

    @GetMapping("/search")
    public List<ResponseSearchItemDto> findItems(
           @RequestParam(required = false) String text,
           @RequestParam(defaultValue = "0") int page,
           @RequestParam(defaultValue = "30") int size
    ) {
        return itemService.findItems(text, page, size);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseCommentDto addComment(
            @RequestHeader(name = SHARER_USER_ID) Long userId,
            @PathVariable Long itemId,
            @RequestBody @Valid RequestCommentDto commentDto
    ) {
        return itemService.addComment(userId, itemId, commentDto);
    }

}
