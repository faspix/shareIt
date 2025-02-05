package com.shareit.item.service;

import com.shareit.booking.mapper.BookingMapper;
import com.shareit.booking.model.Booking;
import com.shareit.booking.repository.BookingRepository;
import com.shareit.booking.utility.BookingStatus;
import com.shareit.exception.NotFoundException;
import com.shareit.exception.ValidationException;
import com.shareit.item.dto.*;
import com.shareit.item.mapper.CommentMapper;
import com.shareit.item.mapper.ItemMapper;
import com.shareit.item.model.Comment;
import com.shareit.item.model.Item;
import com.shareit.item.repository.CommentRepository;
import com.shareit.item.repository.ItemRepository;
import com.shareit.user.model.User;
import com.shareit.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.shareit.item.mapper.ItemMapper.*;
import static com.shareit.user.utility.UserValidator.validateUser;
import static com.shareit.utility.PageRequestMaker.makePageRequest;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final UserService userService;

    private final BookingRepository bookingRepository;

    private final CommentRepository commentRepository;

    private final ItemMapper itemMapper;

    private final BookingMapper bookingMapper;

    private final CommentMapper commentMapper;

    @Override
    @Transactional
    public ResponseItemDtoNoComments addItem(User user, RequestItemDto itemDto) {
        Item item = itemMapper.mapRequestItemDtoToItem(itemDto);
        item.setOwner(user);
        return mapItemToResponseItemDtoNoComments(itemRepository.save(item));
    }

    @Override
    @Transactional
    public ResponseItemDto editItem(User user, Long itemId, RequestItemDto itemDto) {
        Item item = getItem(itemId);
        validateUser(user.getId(), item.getOwner().getId());

        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());

        return itemMapper.mapItemToResponseItemDto(itemRepository.save(item));
    }

    @Override
    public Item getItem(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(
                () -> new NotFoundException("Item with ID " + itemId + " not found")
        );
    }


    @Override
    public List<OwnerResponseItemDto> getAllUsersItems(User user, int page, int size) {
        Pageable pageRequest = makePageRequest(page, size);

        Page<Item> items = itemRepository
                .getAllByOwner(user, pageRequest);


        return items.stream()
                .map(itemMapper::mapItemToOwnerResponseItemDto)
                .peek(item -> {
                    item.setNextBooking(
                            bookingMapper.mapBookingToResponseBookingDto(
                                    bookingRepository
                                            .findFirstBookingByItemAndStatusAndStartAfterOrderByStart
                                                    (mapOwnerResponseItemDtoToItem(item),
                                                            BookingStatus.APPROVED,
                                                            LocalDate.now()
                                                    )
                            )
                    );
                    item.setLastBooking(
                            bookingMapper.mapBookingToResponseBookingDto(
                                    bookingRepository
                                            .findFirstBookingByItemAndStatusAndEndBeforeOrderByEndDesc
                                                    (mapOwnerResponseItemDtoToItem(item),
                                                            BookingStatus.APPROVED,
                                                            LocalDate.now()
                                                    )
                            )
                    );
                })
                .toList();
    }


    @Override
    public List<ResponseSearchItemDto> findItems(String query, int page, int size) {
        Pageable requestPage = makePageRequest(page, size);
        return itemRepository
                .searchItems(query, requestPage)
                .map(ItemMapper::mapItemToResponseSearchItemDto)
                .toList();
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> deleteItem(User user, Long itemId) {
        Item item = getItem(itemId);
        validateUser(user.getId(), item.getOwner().getId());
        itemRepository.deleteById(itemId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseCommentDto addComment(User user, Long itemId, RequestCommentDto commentDto) {
        Item item = getItem(itemId);

        Comment existingComment = commentRepository.findFirstCommentByAuthorAndItem(user, item);
        if (existingComment != null) {
            throw new ValidationException("User ID " + user.getId() + " already commented this item");
        }

        Booking booking = bookingRepository
                .findFirstBookingByBookerAndStatusAndEndBefore(user, BookingStatus.APPROVED, LocalDate.now());
        if (booking == null) {
            throw new ValidationException("User ID " + user.getId() + " didn't book this item");
        }

        Comment comment = new Comment();
        comment.setItem(item);
        comment.setAuthor(user);
        comment.setText(commentDto.getText());
        commentRepository.save(comment);
        return commentMapper.mapCommentToResponseCommentDto(comment);
    }



}

