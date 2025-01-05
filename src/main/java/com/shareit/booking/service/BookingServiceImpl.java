package com.shareit.booking.service;

import com.shareit.booking.dto.ResponseBookingDto;
import com.shareit.booking.dto.RequestBookingDto;
import com.shareit.booking.mapper.BookingMapper;
import com.shareit.booking.model.Booking;
import com.shareit.booking.repository.BookingRepository;
import com.shareit.booking.utility.BookingState;
import com.shareit.booking.utility.BookingStatus;
import com.shareit.exception.NotFoundException;
import com.shareit.exception.ValidationException;
import com.shareit.item.model.Item;
import com.shareit.item.service.ItemService;
import com.shareit.user.model.User;
import com.shareit.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.shareit.booking.mapper.BookingMapper.*;
import static com.shareit.user.mapper.UserMapper.*;
import static com.shareit.item.mapper.ItemMapper.*;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    private final UserService userService;

    private final ItemService itemService;

    public BookingServiceImpl(BookingRepository bookingRepository, UserService userService, ItemService itemService) {
        this.bookingRepository = bookingRepository;
        this.userService = userService;
        this.itemService = itemService;
    }

    @Override
    public ResponseBookingDto createBooking(Long userId, RequestBookingDto requestDto) {

        LocalDate startDate = requestDto.getStart();
        LocalDate endDate = requestDto.getEnd();
        Item bookedItem = mapResponseItemDtoToItem(itemService.getItem(requestDto.getItemId()));

        if (! bookedItem.getAvailable()) {
            throw new ValidationException("This item is not available for reservation");
        }

        List<Booking> existingBooking = bookingRepository
                .getBookingByItemAndStatusAndStartBetweenOrEndBetween
                        (bookedItem, BookingStatus.APPROVED, startDate, endDate, startDate, endDate);
        if (! existingBooking.isEmpty()) {
            throw new ValidationException("This item is not available for reservation from " + startDate + " to " + endDate);
        }

        Booking booking = mapRequestBookingDtoToBooking(requestDto);
        booking.setBooker(mapResponseUserDtoToUser(userService.getUser(userId)));
        booking.setItem(bookedItem);
        booking.setStatus(BookingStatus.WAITING);
        bookingRepository.save(booking);
        return mapBookingToResponseDto(booking);
    }

    @Override
    public ResponseBookingDto approveBooking(Long userId, Long bookingId, Boolean approvedStatus) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(
                () -> new NotFoundException("Booking with ID " + bookingId + " not found")
        );

        if (! booking.getStatus().equals(BookingStatus.WAITING)) {
            throw new ValidationException("Booking status is not WAITING. This bookings cannot be updated");
        }

        User owner = booking.getItem().getOwner();
        validateUser(userId, owner.getId());
        booking.setStatus(approvedStatus ? BookingStatus.APPROVED : BookingStatus.REJECTED);
        return mapBookingToResponseDto(bookingRepository.save(booking));
    }

    @Override
    public ResponseBookingDto getBooking(Long userId, Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId).orElseThrow(
                () -> new NotFoundException("Booking with ID " + bookingId + " not found")
        );

        if (!booking.getBooker().getId().equals(userId)
                && !booking.getItem().getOwner().getId().equals(userId)) {
            throw new ValidationException("User with ID " + userId + " doesn't own or booked this item");
        }
        return mapBookingToResponseDto(booking);
    }

    @Override
    public List<ResponseBookingDto> getAllUserBookings (Long userId, BookingState state){
        User booker = mapResponseUserDtoToUser(userService.getUser(userId));
        List<Booking> list = switch (state) {
            case ALL -> bookingRepository
                    .getBookingsByBookerOrderByStartDesc(booker);
            case CURRENT -> bookingRepository
                    .getBookingsByBookerAndStatusAndStartIsBeforeAndEndIsAfterOrderByStartDesc
                            (booker, BookingStatus.APPROVED, LocalDate.now(), LocalDate.now());
            case PAST -> bookingRepository
                    .getBookingsByBookerAndStatusAndEndIsBeforeOrderByStartDesc
                            (booker, BookingStatus.APPROVED, LocalDate.now());
            case FUTURE -> bookingRepository
                    .getBookingsByBookerAndStatusAndStartIsAfterOrderByStartDesc
                            (booker, BookingStatus.APPROVED, LocalDate.now());
            case WAITING -> bookingRepository
                    .getBookingsByBookerAndStatusOrderByStartDesc(booker, BookingStatus.WAITING);
            case REJECTED -> bookingRepository
                    .getBookingsByBookerAndStatusOrderByStartDesc(booker, BookingStatus.REJECTED);
        };

        return list.stream()
                .map(BookingMapper::mapBookingToResponseDto)
                .toList();
    }


    @Override
    public List<ResponseBookingDto> getOwnerBookings(Long userId, BookingState state) {
        User owner = mapResponseUserDtoToUser(userService.getUser(userId));
        List<Booking> list = switch (state) {
            case ALL -> bookingRepository
                    .findBookingsByItemOwnerOrderByStart(owner);
            case CURRENT -> bookingRepository
                    .findBookingsByItemOwnerAndStatusAndStartIsBeforeAndEndIsAfterOrderByStart
                            (owner, BookingStatus.APPROVED, LocalDate.now(), LocalDate.now());
            case PAST -> bookingRepository
                    .findBookingsByItemOwnerAndStatusAndEndIsBeforeOrderByStart
                            (owner, BookingStatus.APPROVED, LocalDate.now());
            case FUTURE -> bookingRepository
                    .findBookingsByItemOwnerAndStatusAndStartIsAfterOrderByStart
                            (owner, BookingStatus.APPROVED, LocalDate.now());
            case WAITING -> bookingRepository
                    .findBookingsByItemOwnerAndStatusOrderByStart(owner, BookingStatus.WAITING);
            case REJECTED -> bookingRepository
                    .findBookingsByItemOwnerAndStatusOrderByStart(owner, BookingStatus.REJECTED);
        };

        return list.stream()
                .map(BookingMapper::mapBookingToResponseDto)
                .toList();
    }




    private void validateUser(Long userId, Long ItemUserId) {
        if (! userId.equals(ItemUserId))
            throw new ValidationException("User ID " + userId + " does not own this item");
    }

}
