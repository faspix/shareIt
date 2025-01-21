package com.shareit.booking;

import com.shareit.booking.dto.ResponseBookingDto;
import com.shareit.booking.dto.RequestBookingDto;
import com.shareit.booking.mapper.BookingMapper;
import com.shareit.booking.service.BookingService;
import com.shareit.booking.utility.BookingState;
import com.shareit.security.SecurityUser;
import com.shareit.user.mapper.UserMapper;
import com.shareit.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {


    private final BookingService bookingService;

    private final BookingMapper bookingMapper;

    @PostMapping
    public ResponseBookingDto createBookingBooking(
            @AuthenticationPrincipal SecurityUser userPrincipal,
            @RequestBody @Valid RequestBookingDto bookingDto
    ) {
        return bookingService.createBooking(userPrincipal.getUser(), bookingDto);
    }

    @PatchMapping("/{bookingId}")
    public ResponseBookingDto approveBooking(
            @AuthenticationPrincipal SecurityUser userPrincipal,
            @PathVariable Long bookingId,
            @RequestParam(name = "approved") Boolean approvedStatus
    ) {
        return bookingService.approveBooking(userPrincipal.getUser(), bookingId, approvedStatus);
    }

    @GetMapping("/{bookingId}")
    public ResponseBookingDto getBooking(
            @AuthenticationPrincipal SecurityUser userPrincipal,
            @PathVariable Long bookingId
    ) {
        return bookingMapper.mapBookingToResponseBookingDto(bookingService.getBooking(userPrincipal.getUser(), bookingId));
    }

    @GetMapping
    public List<ResponseBookingDto> getAllUserBookings(
            @AuthenticationPrincipal SecurityUser userPrincipal,
            @RequestParam(defaultValue = "ALL") BookingState state,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size
    ) {
        return bookingService.getAllUserBookings(userPrincipal.getUser(), state, page, size);
    }

    @GetMapping("/owner")
    public List<ResponseBookingDto> getOwnerBookings(
            @AuthenticationPrincipal SecurityUser userPrincipal,
            @RequestParam(defaultValue = "ALL") BookingState state,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size
    ) {
        return bookingService.getOwnerBookings(userPrincipal.getUser(), state, page, size);
    }

}
