package com.shareit.booking.service;

import com.shareit.booking.dto.RquestBookingDto;
import com.shareit.booking.dto.RequestBookingDto;
import com.shareit.booking.mapper.BookingMapper;
import com.shareit.booking.model.Booking;
import com.shareit.booking.repository.BookingRepository;
import com.shareit.booking.utility.BookingState;
import com.shareit.booking.utility.BookingStatus;
import com.shareit.exception.BookingNotFoundException;
import com.shareit.exception.UserValidationException;
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
    public RquestBookingDto createBooking(Long userId, RequestBookingDto requestDto) {
        Booking booking = mapRequestBookingDtoToBooking(requestDto);
        booking.setBooker(mapResponseUserDtoToUser(userService.getUser(userId)));
        booking.setItem(mapResponseItemDtoToItem(itemService.getItem(requestDto.getItemId())));
        booking.setStatus(BookingStatus.WAITING);
        bookingRepository.save(booking);
        return mapBookingToDto(booking);
    }

    @Override
    public RquestBookingDto approveBooking(Long userId, Long bookingId, Boolean approvedStatus) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(
                () -> new BookingNotFoundException(bookingId)
        );
        User owner = booking.getItem().getOwner();
        validateUser(userId, owner.getId());
        booking.setStatus(approvedStatus ? BookingStatus.APPROVED : BookingStatus.REJECTED);
        return mapBookingToDto(bookingRepository.save(booking));
    }

    @Override
    public RquestBookingDto getBooking(Long userId, Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId).orElseThrow(
                () -> new BookingNotFoundException(bookingId)
        );

        if (!booking.getBooker().getId().equals(userId)
                && !booking.getItem().getOwner().getId().equals(userId)) {
            throw new UserValidationException("User with ID " + userId + " doesn't own or booked this item");
        }
        return mapBookingToDto(booking);
    }

    @Override
    public List<RquestBookingDto> getAllUserBookings (Long userId, BookingState state){
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
                .map(BookingMapper::mapBookingToDto)
                .toList();
    }


    @Override
    public List<RquestBookingDto> getOwnerBookings(Long userId, BookingState state) {
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
                .map(BookingMapper::mapBookingToDto)
                .toList();
    }


    private void validateUser(Long userId, Long ItemUserId) {
        if (! userId.equals(ItemUserId))
            throw new UserValidationException("User ID " + userId + " does not own this item");
    }

}
