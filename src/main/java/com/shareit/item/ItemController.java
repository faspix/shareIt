package com.shareit.item;

import com.shareit.item.dto.ItemDto;
import com.shareit.item.model.Item;
import com.shareit.item.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@RequestMapping("/items")
public class ItemController {

    private static final String SHARER_USER_ID = "X-Sharer-User-Id";

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ItemDto addItem(
            @RequestHeader(name = SHARER_USER_ID) Long userId,
            @RequestBody ItemDto itemDto
    ) {
        return itemService.addItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ItemDto editItem( // ResponseEntity<HttpStatus>
            @RequestHeader(name = SHARER_USER_ID) Long userId,
            @PathVariable Long itemId
    ) {
        return itemService.editItem(userId, itemId); // ResponseEntity.ok(HttpStatus.OK)
    }

    @GetMapping("/{itemId}")
    public ItemDto getItem(
            @RequestHeader(name = SHARER_USER_ID) Long userId,
            @PathVariable Long itemId
    ) {
        return itemService.getItem(userId, itemId);
    }

    @GetMapping
    public Set<ItemDto> getAllUsersItems(
            @RequestHeader(name = SHARER_USER_ID) Long userId
    ) {
        return itemService.getAllUsersItems(userId);
    }

    @GetMapping("/search")
    public Set<ItemDto> findItems(
           // @RequestHeader(name = SHARER_USER_ID) Long userId,
           @RequestParam String text
    ) {
        return itemService.findItems(text);
    }

}
