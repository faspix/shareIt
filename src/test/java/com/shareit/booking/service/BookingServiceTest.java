package com.shareit.booking.service;

import com.shareit.booking.mapper.BookingMapper;
import com.shareit.booking.model.Booking;
import com.shareit.booking.repository.BookingRepository;
import com.shareit.booking.utility.BookingStatus;
import com.shareit.exception.ValidationException;
import com.shareit.item.service.ItemService;
import com.shareit.user.mapper.UserMapper;
import com.shareit.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static com.shareit.booking.utils.BookingUtils.*;
import static com.shareit.item.utils.ItemUtils.ITEM_TEST;
import static com.shareit.user.utils.UserUtils.USER_TEST;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    private BookingService bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private UserService userService;

    @Mock
    private ItemService itemService;


    private final Booking BOOKING_TEST_APPROVING = Booking.builder()
            .id(BOOKING_ID_TEST)
            .status(BookingStatus.WAITING)
            .start(LocalDate.now())
            .end(LocalDate.now())
            .booker(USER_TEST)
            .item(ITEM_TEST)
            .build();


    @BeforeEach
    public void init() {
        bookingService = new BookingServiceImpl(
         bookingRepository,
                userService,
                itemService,
         new BookingMapper(new UserMapper(new BCryptPasswordEncoder()))
        );
    }


    @Test
    public void getBookingTest() {
        when(bookingRepository.findById(anyLong()))
                .thenReturn(Optional.of(BOOKING_TEST));

        var booking = bookingService.getBooking(USER_TEST, BOOKING_ID_TEST);

        assertEquals(booking.getId(), BOOKING_TEST.getId());
        assertEquals(booking.getStatus(), BOOKING_TEST.getStatus());
        assertEquals(booking.getItem(), BOOKING_TEST.getItem());
        assertEquals(booking.getStart(), BOOKING_TEST.getStart());
        assertEquals(booking.getEnd(), BOOKING_TEST.getEnd());
        assertEquals(booking.getBooker(), BOOKING_TEST.getBooker());
    }

    @Test
    public void createBookingTest() {
        when(itemService.getItem(anyLong()))
                .thenReturn(ITEM_TEST);

        when(bookingRepository.checkItemAvailableForBooking(any(), any(), any()))
                .thenReturn(0);

        var booking = bookingService.createBooking(USER_TEST, REQUEST_BOOKING_DTO_TEST);

        assertEquals(booking.getEnd(), REQUEST_BOOKING_DTO_TEST.getEnd());
        assertEquals(booking.getStart(), REQUEST_BOOKING_DTO_TEST.getStart());
        assertEquals(booking.getItem().getId(), REQUEST_BOOKING_DTO_TEST.getItemId());
    }


    @Test
    public void approveBookingTest_ValidationError() {
        when(bookingRepository.findById(anyLong()))
                .thenReturn(Optional.of(BOOKING_TEST));

        assertThrows(ValidationException.class, () ->
            bookingService.approveBooking(USER_TEST, BOOKING_ID_TEST, true)
        );

    }

    @Test
    public void approveBookingTest_True() {
        when(bookingRepository.findById(anyLong()))
                .thenReturn(Optional.of(BOOKING_TEST_APPROVING));
        when(bookingRepository.save(any()))
                .thenReturn(BOOKING_TEST_APPROVING);

        var result = bookingService.approveBooking(USER_TEST, BOOKING_ID_TEST, true);
        assertEquals(result.getStatus(), BookingStatus.APPROVED);
        assertEquals(result.getId(), BOOKING_TEST_APPROVING.getId());
        assertEquals(result.getStart(), BOOKING_TEST_APPROVING.getStart());
        assertEquals(result.getEnd(), BOOKING_TEST_APPROVING.getEnd());
    }

    @Test
    public void approveBookingTest_False() {
        when(bookingRepository.findById(anyLong()))
                .thenReturn(Optional.of(BOOKING_TEST_APPROVING));
        when(bookingRepository.save(any()))
                .thenReturn(BOOKING_TEST_APPROVING);

        var result = bookingService.approveBooking(USER_TEST, BOOKING_ID_TEST, false);
        assertEquals(result.getStatus(), BookingStatus.REJECTED);
        assertEquals(result.getId(), BOOKING_TEST_APPROVING.getId());
        assertEquals(result.getStart(), BOOKING_TEST_APPROVING.getStart());
        assertEquals(result.getEnd(), BOOKING_TEST_APPROVING.getEnd());
    }



}
