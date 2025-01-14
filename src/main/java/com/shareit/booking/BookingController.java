package com.shareit.booking;

import com.shareit.booking.dto.ResponseBookingDto;
import com.shareit.booking.dto.RequestBookingDto;
import com.shareit.booking.service.BookingService;
import com.shareit.booking.utility.BookingState;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.shareit.booking.mapper.BookingMapper.mapBookingToResponseBookingDto;


@RestController
@RequestMapping(path = "/bookings")
public class BookingController {

    private static final String SHARER_USER_ID = "X-Sharer-User-Id";

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseBookingDto createBookingBooking(
            @RequestHeader(name = SHARER_USER_ID) Long userId,
            @RequestBody @Valid RequestBookingDto bookingDto
    ) {
        return bookingService.createBooking(userId, bookingDto);
    }

    @PatchMapping("/{bookingId}")
    public ResponseBookingDto approveBooking(
            @RequestHeader(name = SHARER_USER_ID) Long userId,
            @PathVariable Long bookingId,
            @RequestParam(name = "approved") Boolean approvedStatus
    ) {
        return bookingService.approveBooking(userId, bookingId, approvedStatus);
    }

    @GetMapping("/{bookingId}")
    public ResponseBookingDto getBooking(
            @RequestHeader(name = SHARER_USER_ID) Long userId,
            @PathVariable Long bookingId
    ) {
        return mapBookingToResponseBookingDto(bookingService.getBooking(userId, bookingId));
    }

    @GetMapping
    public List<ResponseBookingDto> getAllUserBookings(
            @RequestHeader(name = SHARER_USER_ID) Long userId,
            @RequestParam(defaultValue = "ALL") BookingState state,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size
    ) {
        return bookingService.getAllUserBookings(userId, state, page, size);
    }

    @GetMapping("/owner")
    public List<ResponseBookingDto> getOwnerBookings(
            @RequestHeader(name = SHARER_USER_ID) Long userId,
            @RequestParam(defaultValue = "ALL") BookingState state,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size
    ) {
        return bookingService.getOwnerBookings(userId, state, page, size);
    }


}
