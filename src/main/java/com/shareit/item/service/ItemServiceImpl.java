package com.shareit.item.service;

import com.shareit.booking.model.Booking;
import com.shareit.booking.repository.BookingRepository;
import com.shareit.booking.utility.BookingStatus;
import com.shareit.exception.NotFoundException;
import com.shareit.exception.ValidationException;
import com.shareit.item.dto.*;
import com.shareit.item.mapper.ItemMapper;
import com.shareit.item.model.Comment;
import com.shareit.item.model.Item;
import com.shareit.item.repository.CommentRepository;
import com.shareit.item.repository.ItemRepository;
import com.shareit.user.model.User;
import com.shareit.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.shareit.booking.mapper.BookingMapper.mapBookingToResponseDto;
import static com.shareit.item.mapper.CommentMapper.mapCommentToResponseCommentDto;
import static com.shareit.item.mapper.ItemMapper.*;
import static com.shareit.user.mapper.UserMapper.*;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final UserService userService;

    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    public ItemServiceImpl(ItemRepository itemRepository,
                           UserService userService,
                           BookingRepository bookingRepository,
                           CommentRepository commentRepository) {
        this.itemRepository = itemRepository;
        this.userService = userService;
        this.bookingRepository = bookingRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional
    public ResponseItemDtoNoComments addItem(Long userId,
                                             RequestItemDto itemDto) {

        User user = mapResponseUserDtoToUser(userService.getUser(userId));
        Item item = mapRequestItemDtoToItem(itemDto);
        item.setOwner(user);
        return mapItemToResponseItemDtoNoComments(itemRepository.save(item));
    }

    @Override
    @Transactional
    public ResponseItemDto editItem(Long userId,
                                    Long itemId,
                                    RequestItemDto itemDto) {

        Item item = (itemRepository.findById(itemId).orElseThrow(
                () -> new NotFoundException("Item with ID " + itemId + " not found")
        ));

        validateUser(userId, item.getOwner().getId());

        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());

        return mapItemToResponseItemDto(itemRepository.save(item));
    }

    @Override
    public ResponseItemDto getItem(Long itemId) {
        Item item = (itemRepository.findById(itemId).orElseThrow(
                () -> new NotFoundException("Item with ID " + itemId + " not found")
        ));
        return mapItemToResponseItemDto(item);
    }


    @Override
    public List<OwnerResponseItemDto> getAllUsersItems(Long userId) {
        User user = mapResponseUserDtoToUser(userService.getUser(userId));
        List<Item> items = itemRepository.getAllByOwner(user);

        return items.stream()
                .map(ItemMapper::mapItemToOwnerResponseItemDto)
                .peek(item -> {
                    item.setNextBooking(mapBookingToResponseDto(
                                    bookingRepository
                                            .findFirstBookingByItemAndStatusAndStartAfterOrderByStart
                                                    (mapOwnerResponseItemDtoToItem(item),
                                                            BookingStatus.APPROVED,
                                                            LocalDate.now())
                    ));
                    item.setLastBooking(mapBookingToResponseDto(
                                    bookingRepository
                                            .findFirstBookingByItemAndStatusAndEndBeforeOrderByEndDesc
                                                    (mapOwnerResponseItemDtoToItem(item),
                                                            BookingStatus.APPROVED,
                                                            LocalDate.now())
                    ));
                })
                .toList();
    }


    @Override
    public List<ResponseItemDto> findItems(String text) { // TODO:
        List<Item> items = itemRepository.findItemsByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(text, text);
        return items.stream()
                .map(ItemMapper::mapItemToResponseItemDto)
                .toList();
    }

    @Override
    @Transactional
    public void deleteItem(Long userId,
                           Long itemId) {

        Item item = (itemRepository.findById(itemId).orElseThrow(
                () -> new NotFoundException("Item with ID " + itemId + " not found")
        ));
        validateUser(userId, item.getOwner().getId());
        itemRepository.deleteById(itemId);
    }

    @Override
    @Transactional
    public ResponseCommentDto addComment(Long userId,
                                         Long itemId,
                                         RequestCommentDto commentDto) {

        User user = mapResponseUserDtoToUser(userService.getUser(userId));
        Item item = mapResponseItemDtoToItem(getItem(itemId));

        Comment existingComment = commentRepository.findFirstCommentByAuthorAndItem(user, item);
        if (existingComment != null) {
            throw new ValidationException("User ID " + userId + " already commented this item");
        }

        Booking booking = bookingRepository
                .findFirstBookingByBookerAndStatusAndEndBefore(user, BookingStatus.APPROVED, LocalDate.now());
        if (booking == null) {
            throw new ValidationException("User ID " + userId + " didn't book this item");
        }

        Comment comment = new Comment();

        comment.setItem(item);
        comment.setAuthor(user);
        comment.setText(commentDto.getText());

        commentRepository.save(comment);
        return mapCommentToResponseCommentDto(comment);
    }


    private void validateUser(Long userId, Long ItemUserId) {
        if (! userId.equals(ItemUserId))
            throw new ValidationException("User ID " + userId + " does not own this item");
    }

}

