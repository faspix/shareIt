package com.shareit.item;

import com.shareit.item.dto.RequestItemDto;
import com.shareit.item.dto.ResponseItemDto;
import com.shareit.item.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/items")
public class ItemController {

    private static final String SHARER_USER_ID = "X-Sharer-User-Id";

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseItemDto addItem(
            @RequestHeader(name = SHARER_USER_ID) Long userId,
            @RequestBody @Valid RequestItemDto itemDto
    ) {
        return itemService.addItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ResponseItemDto editItem( // ResponseEntity<HttpStatus>
            @RequestHeader(name = SHARER_USER_ID) Long userId,
            @PathVariable Long itemId,
            @RequestBody @Valid RequestItemDto itemDto
    ) {
        return itemService.editItem(userId, itemId, itemDto); // ResponseEntity.ok(HttpStatus.OK)
    }

    @GetMapping("/{itemId}")
    public ResponseItemDto getItem(
            @RequestHeader(name = SHARER_USER_ID) Long userId,
            @PathVariable Long itemId
    ) {
        return itemService.getItem(itemId);
    }

    @GetMapping
    public List<ResponseItemDto> getAllUsersItems(
            @RequestHeader(name = SHARER_USER_ID) Long userId
    ) {
        return itemService.getAllUsersItems(userId);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(
            @RequestHeader(name = SHARER_USER_ID) Long userId,
            @PathVariable Long itemId
    ) {
        itemService.deleteItem(userId, itemId);
    }

    @GetMapping("/search")
    public List<ResponseItemDto> findItems(
           // @RequestHeader(name = SHARER_USER_ID) Long userId,
           @RequestParam(required = false) String text
    ) {
        return itemService.findItems(text);
    }

}
