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

    private final BookingMapper bookingMapper;

    @Override
    public ResponseBookingDto createBooking(User user, RequestBookingDto requestDto) {
        LocalDate startDate = requestDto.getStart();
        LocalDate endDate = requestDto.getEnd();
        Item bookedItem = itemService.getItem(requestDto.getItemId());

        validateNewBooking(startDate, endDate, bookedItem);

        Booking booking = bookingMapper.mapRequestBookingDtoToBooking(requestDto);
        booking.setBooker(user);
        booking.setItem(bookedItem);
        booking.setStatus(BookingStatus.WAITING);

        bookingRepository.save(booking);
        return bookingMapper.mapBookingToResponseBookingDto(booking);
    }



    @Override
    public ResponseBookingDto approveBooking(User user, Long bookingId, Boolean approvedStatus) {
        Booking booking = getBooking(user, bookingId);

        if (! booking.getStatus().equals(BookingStatus.WAITING)) {
            throw new ValidationException("Booking status is not WAITING. This bookings cannot be updated");
        }

        User owner = booking.getItem().getOwner();
        validateUser(user.getId(), owner.getId());
        booking.setStatus(approvedStatus ? BookingStatus.APPROVED : BookingStatus.REJECTED);
        return bookingMapper.mapBookingToResponseBookingDto(bookingRepository.save(booking));
    }

    @Override
    public Booking getBooking(User user, Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId).orElseThrow(
                () -> new NotFoundException("Booking with ID " + bookingId + " not found")
        );

        if (!booking.getBooker().equals(user)
                && !booking.getItem().getOwner().equals(user)) {
            throw new ValidationException("User with ID " + user.getId() + " doesn't own or booked this item");
        }
        return booking;
    }

    @Override
    public List<ResponseBookingDto> getAllUserBookings (User booker, BookingState state, int page, int size){
//        User booker = userService.findUser(userId);
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
                .map(bookingMapper::mapBookingToResponseBookingDto)
                .toList();
    }


    @Override
    public List<ResponseBookingDto> getOwnerBookings(User owner, BookingState state, int page, int size) {
//        User owner = userService.findUser(userId);
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
                .map(bookingMapper::mapBookingToResponseBookingDto)
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
