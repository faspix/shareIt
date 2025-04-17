package com.shareit.item.service;

import com.shareit.booking.mapper.BookingMapper;
import com.shareit.booking.model.Booking;
import com.shareit.booking.repository.BookingRepository;
import com.shareit.exception.NotFoundException;
import com.shareit.exception.ValidationException;
import com.shareit.item.dto.OwnerResponseItemDto;
import com.shareit.item.dto.ResponseSearchItemDto;
import com.shareit.item.mapper.CommentMapper;
import com.shareit.item.mapper.ItemMapper;
import com.shareit.item.model.Comment;
import com.shareit.item.model.Item;
import com.shareit.item.repository.CommentRepository;
import com.shareit.item.repository.ItemRepository;
import com.shareit.user.mapper.UserMapper;
import com.shareit.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static com.shareit.item.utils.ItemUtils.*;
import static com.shareit.user.utils.UserUtils.USER_TEST;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private UserService userService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private BookingMapper bookingMapper;

    private ItemService itemService;

    @BeforeEach
    public void init() {
        itemService = new ItemServiceImpl(itemRepository,
                userService,
                bookingRepository,
                commentRepository,
                new ItemMapper(new CommentMapper(new UserMapper(new BCryptPasswordEncoder()))),
                bookingMapper,
                new CommentMapper(new UserMapper(new BCryptPasswordEncoder())));
    }

    @Test
    void addItemTest() {
        when(itemRepository.save(any()))
                .thenReturn(ITEM_TEST);

        var item = itemService.addItem(USER_TEST, REQUEST_ITEM_DTO_TEST);

        assertEquals(item.getName(), REQUEST_ITEM_DTO_TEST.getName());
        assertEquals(item.getAvailable(), REQUEST_ITEM_DTO_TEST.getAvailable());
        assertEquals(item.getDescription(), REQUEST_ITEM_DTO_TEST.getDescription());

    }

    @Test
    void getItemTest() {
        when(itemRepository.findById(anyLong()))
                .thenReturn(Optional.of(ITEM_TEST));

        var user = itemService.getItem(ITEM_ID_TEST);

        assertEquals(user.getId(), ITEM_TEST.getId());
        assertEquals(user.getName(), ITEM_TEST.getName());
        assertEquals(user.getDescription(), ITEM_TEST.getDescription());
        assertEquals(user.getComments(), ITEM_TEST.getComments());
        assertEquals(user.getOwner(), ITEM_TEST.getOwner());
        assertEquals(user.getAvailable(), ITEM_TEST.getAvailable());

    }

    @Test
    void editItemTest() {
        when(itemRepository.findById(anyLong()))
                .thenReturn(Optional.of(ITEM_TEST));
        when(itemRepository.save(any()))
                .thenReturn(ITEM_TEST);

        var user = itemService.editItem(USER_TEST, ITEM_ID_TEST, REQUEST_ITEM_DTO_TEST);

        assertEquals(user.getName(), REQUEST_ITEM_DTO_TEST.getName());
        assertEquals(user.getAvailable(), REQUEST_ITEM_DTO_TEST.getAvailable());
        assertEquals(user.getDescription(), REQUEST_ITEM_DTO_TEST.getDescription());
    }

    @Test
    void getItemTest_Error() {
        when(itemRepository.findById(any()))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
                itemService.getItem(null)
        );

    }

    @Test
    void addCommentTest() {
        when(commentRepository.findFirstCommentByAuthorAndItem(any(), any()))
                .thenReturn(null);
        when(bookingRepository.findFirstBookingByBookerAndStatusAndEndBefore(any(), any(), any()))
                .thenReturn(new Booking());
        when(itemRepository.findById(anyLong()))
                .thenReturn(Optional.of(ITEM_TEST));

        var comment = itemService.addComment(USER_TEST, ITEM_ID_TEST, REQUEST_COMMENT_DTO_TEST);

        assertEquals(comment.getText(), REQUEST_COMMENT_DTO_TEST.getText());
    }

    @Test
    void addCommentTest_ErrorOnBooking() {
        when(commentRepository.findFirstCommentByAuthorAndItem(any(), any()))
                .thenReturn(null);
        when(bookingRepository.findFirstBookingByBookerAndStatusAndEndBefore(any(), any(), any()))
                .thenReturn(null);
        when(itemRepository.findById(anyLong()))
                .thenReturn(Optional.of(ITEM_TEST));

        assertThrows(ValidationException.class, () ->
                itemService.addComment(USER_TEST, ITEM_ID_TEST, REQUEST_COMMENT_DTO_TEST)
        );

    }

    @Test
    void addCommentTest_ErrorOnCommentDuplicate() {
        when(commentRepository.findFirstCommentByAuthorAndItem(any(), any()))
                .thenReturn(new Comment());
        when(itemRepository.findById(anyLong()))
                .thenReturn(Optional.of(ITEM_TEST));

        assertThrows(ValidationException.class, () ->
                itemService.addComment(USER_TEST, ITEM_ID_TEST, REQUEST_COMMENT_DTO_TEST)
        );

    }

    @Test
    void getAllUsersItemsTest() {
        Page<Item> page = new PageImpl<>(List.of(ITEM_TEST));
        when(itemRepository.getAllByOwner(any(), any()))
                .thenReturn(page);
        when(bookingRepository.findFirstBookingByItemAndStatusAndStartAfterOrderByStart(any(), any(), any()))
                .thenReturn(null);
        when(bookingRepository.findFirstBookingByItemAndStatusAndEndBeforeOrderByEndDesc(any(), any(), any()))
                .thenReturn(null);

        List<OwnerResponseItemDto> result = itemService.getAllUsersItems(USER_TEST, 0, 10);

        assertEquals(1, result.size());
        assertEquals(ITEM_TEST.getId(), result.get(0).getId());
        assertEquals(ITEM_TEST.getName(), result.get(0).getName());
    }

    @Test
    void findItemsTest() {
        Page<Item> page = new PageImpl<>(List.of(ITEM_TEST));
        when(itemRepository.searchItems(anyString(), any()))
                .thenReturn(page);

        List<ResponseSearchItemDto> result = itemService.findItems("test", 0, 10);

        assertEquals(1, result.size());
        assertEquals(ITEM_TEST.getId(), result.get(0).getId());
        assertEquals(ITEM_TEST.getName(), result.get(0).getName());
    }

    @Test
    void findItemsTest_EmptyQuery() {
        Page<Item> page = new PageImpl<>(List.of());
        when(itemRepository.searchItems(anyString(), any()))
                .thenReturn(page);

        List<ResponseSearchItemDto> result = itemService.findItems("", 0, 10);

        assertEquals(0, result.size());
    }

}
