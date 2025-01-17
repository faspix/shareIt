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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.shareit.booking.mapper.BookingMapper.*;
import static com.shareit.user.utility.UserValidator.validateUser;
import static com.shareit.utility.pageRequestMaker.makePageRequest;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    private final UserService userService;

    private final ItemService itemService;

    @Override
    public ResponseBookingDto createBooking(Long userId, RequestBookingDto requestDto) {
        LocalDate startDate = requestDto.getStart();
        LocalDate endDate = requestDto.getEnd();
        Item bookedItem = itemService.getItem(requestDto.getItemId());

        validateNewBooking(startDate, endDate, bookedItem);

        Booking booking = mapRequestBookingDtoToBooking(requestDto);
        booking.setBooker(userService.findUser(userId));
        booking.setItem(bookedItem);
        booking.setStatus(BookingStatus.WAITING);

        bookingRepository.save(booking);
        return mapBookingToResponseBookingDto(booking);
    }



    @Override
    public ResponseBookingDto approveBooking(Long userId, Long bookingId, Boolean approvedStatus) {
        Booking booking = getBooking(userId, bookingId);

        if (! booking.getStatus().equals(BookingStatus.WAITING)) {
            throw new ValidationException("Booking status is not WAITING. This bookings cannot be updated");
        }

        User owner = booking.getItem().getOwner();
        validateUser(userId, owner.getId());
        booking.setStatus(approvedStatus ? BookingStatus.APPROVED : BookingStatus.REJECTED);
        return mapBookingToResponseBookingDto(bookingRepository.save(booking));
    }

    @Override
    public Booking getBooking(Long userId, Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId).orElseThrow(
                () -> new NotFoundException("Booking with ID " + bookingId + " not found")
        );

        if (!booking.getBooker().getId().equals(userId)
                && !booking.getItem().getOwner().getId().equals(userId)) {
            throw new ValidationException("User with ID " + userId + " doesn't own or booked this item");
        }
        return booking;
    }

    @Override
    public List<ResponseBookingDto> getAllUserBookings (Long userId, BookingState state, int page, int size){
        User booker = userService.findUser(userId);
        Pageable pageRequest = makePageRequest(page, size, Sort.by("start").descending());
        Page<Booking> currentPage = switch (state) {
            case ALL -> bookingRepository
                    .getBookingsByBooker(booker, pageRequest);
            case CURRENT -> bookingRepository
                    .getBookingsByBookerAndStatusAndStartIsBeforeAndEndIsAfter
                            (booker, BookingStatus.APPROVED, LocalDate.now(), LocalDate.now(), pageRequest);
            case PAST -> bookingRepository
                    .getBookingsByBookerAndStatusAndEndIsBefore
                            (booker, BookingStatus.APPROVED, LocalDate.now(), pageRequest);
            case FUTURE -> bookingRepository
                    .getBookingsByBookerAndStatusAndStartIsAfter
                            (booker, BookingStatus.APPROVED, LocalDate.now(), pageRequest);
            case WAITING -> bookingRepository
                    .getBookingsByBookerAndStatus(booker, BookingStatus.WAITING, pageRequest);
            case REJECTED -> bookingRepository
                    .getBookingsByBookerAndStatus(booker, BookingStatus.REJECTED, pageRequest);
        };
        return currentPage
                .map(BookingMapper::mapBookingToResponseBookingDto)
                .toList();
    }


    @Override
    public List<ResponseBookingDto> getOwnerBookings(Long userId, BookingState state, int page, int size) {
        User owner = userService.findUser(userId);
        Pageable pageRequest = makePageRequest(page, size, Sort.by("start").descending());
        Page<Booking> currentPage = switch (state) {
            case ALL -> bookingRepository
                    .findBookingsByItemOwner(owner, pageRequest);
            case CURRENT -> bookingRepository
                    .findBookingsByItemOwnerAndStatusAndStartIsBeforeAndEndIsAfter
                            (owner, BookingStatus.APPROVED, LocalDate.now(), LocalDate.now(), pageRequest);
            case PAST -> bookingRepository
                    .findBookingsByItemOwnerAndStatusAndEndIsBefore
                            (owner, BookingStatus.APPROVED, LocalDate.now(), pageRequest);
            case FUTURE -> bookingRepository
                    .findBookingsByItemOwnerAndStatusAndStartIsAfter
                            (owner, BookingStatus.APPROVED, LocalDate.now(), pageRequest);
            case WAITING -> bookingRepository
                    .findBookingsByItemOwnerAndStatus(owner, BookingStatus.WAITING, pageRequest);
            case REJECTED -> bookingRepository
                    .findBookingsByItemOwnerAndStatus(owner, BookingStatus.REJECTED, pageRequest);
        };
        return currentPage
                .map(BookingMapper::mapBookingToResponseBookingDto)
                .toList();
    }


    private void validateNewBooking(LocalDate startDate, LocalDate endDate, Item bookedItem) {
//        if (startDate.isBefore(LocalDate.now()) || endDate.isBefore(LocalDate.now())) {
//            throw new ValidationException("Invalid reservation dates");
//        }

        if (startDate.isAfter(endDate)) {
            throw new ValidationException("Start date cannot be after end date");
        }

        if (! bookedItem.getAvailable()) {
            throw new ValidationException("This item is not available for reservation");
        }

        int extstBookingCount = bookingRepository.
                checkItemAvailableForBooking(bookedItem, startDate, endDate);
        if (extstBookingCount > 0) {
            log.debug("Number of bookings from {} to {} is {}", startDate, endDate, extstBookingCount);
            throw new ValidationException("This item is not available for reservation from "
                    + startDate + " to " + endDate);
        }
    }

}
