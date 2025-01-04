package com.shareit.booking;

import com.shareit.booking.dto.RquestBookingDto;
import com.shareit.booking.dto.RequestBookingDto;
import com.shareit.booking.service.BookingService;
import com.shareit.booking.utility.BookingState;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/bookings")
public class BookingController {

    private static final String SHARER_USER_ID = "X-Sharer-User-Id";

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public RquestBookingDto createBookingBooking(
            @RequestHeader(name = SHARER_USER_ID) Long userId,
            @RequestBody @Valid RequestBookingDto bookingDto
    ) {
        return bookingService.createBooking(userId, bookingDto);
    }

    @PatchMapping("/{bookingId}")
    public RquestBookingDto approveBooking(
            @RequestHeader(name = SHARER_USER_ID) Long userId,
            @PathVariable Long bookingId,
            @RequestParam(name = "approved") Boolean approvedStatus
    ) {
        return bookingService.approveBooking(userId, bookingId, approvedStatus);
    }

    @GetMapping("/{bookingId}")
    public RquestBookingDto getBooking(
            @RequestHeader(name = SHARER_USER_ID) Long userId,
            @PathVariable Long bookingId
    ) {
        return bookingService.getBooking(userId, bookingId);
    }

    @GetMapping
    public List<RquestBookingDto> getAllUserBookings(
            @RequestHeader(name = SHARER_USER_ID) Long userId,
            @RequestParam(defaultValue = "ALL") BookingState state
    ) {
        return bookingService.getAllUserBookings(userId, state);
    }

    @GetMapping("/owner")
    public List<RquestBookingDto> getOwnerBookings(
            @RequestHeader(name = SHARER_USER_ID) Long userId,
            @RequestParam(defaultValue = "ALL") BookingState state
    ) {
        return bookingService.getOwnerBookings(userId, state);
    }


}
