package com.shareit.booking.repository;

import com.shareit.booking.dto.ResponseBookingDto;
import com.shareit.booking.model.Booking;
import com.shareit.booking.utility.BookingStatus;
import com.shareit.item.dto.ResponseItemDto;
import com.shareit.item.model.Item;
import com.shareit.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    String checkItemAvailableForBookingSQL = "SELECT COUNT(b) FROM Booking b " +
            "WHERE b.item = :item " +
            "AND b.status = 'APPROVED' " +
            "AND ((b.start BETWEEN :startDate AND :endDate) OR (b.end BETWEEN :startDate AND :endDate))";

    @Query(checkItemAvailableForBookingSQL)
    Integer checkItemAvailableForBooking(
             Item item,
             LocalDate startDate,
             LocalDate endDate
    );



    Page<Booking> getBookingsByBooker(User booker, Pageable pageRequest);

    Page<Booking> getBookingsByBookerAndStatus(User booker,
                                                               BookingStatus bookingStatus,
                                                               Pageable pageRequest);

    Page<Booking> getBookingsByBookerAndStatusAndStartIsAfter(User booker,
                                                                              BookingStatus bookingStatus,
                                                                              LocalDate now,
                                                                              Pageable pageRequest);

    Page<Booking> getBookingsByBookerAndStatusAndEndIsBefore(User booker,
                                                                             BookingStatus bookingStatus,
                                                                             LocalDate now,
                                                                             Pageable pageRequest);

    Page<Booking> getBookingsByBookerAndStatusAndStartIsBeforeAndEndIsAfter(User booker,
                                                                                            BookingStatus bookingStatus,
                                                                                            LocalDate now,
                                                                                            LocalDate now1,
                                                                                            Pageable pageRequest);

    Page<Booking> findBookingsByItemOwner(User owner, Pageable pageRequest);

    Page<Booking> findBookingsByItemOwnerAndStatus(User owner,
                                                               BookingStatus bookingStatus,
                                                               Pageable pageRequest);

    Page<Booking> findBookingsByItemOwnerAndStatusAndEndIsBefore(User owner,
                                                                             BookingStatus bookingStatus,
                                                                             LocalDate now,
                                                                             Pageable pageRequest);

    Page<Booking> findBookingsByItemOwnerAndStatusAndStartIsAfter(User owner,
                                                                              BookingStatus bookingStatus,
                                                                              LocalDate now,
                                                                              Pageable pageRequest);

    Page<Booking> findBookingsByItemOwnerAndStatusAndStartIsBeforeAndEndIsAfter(User owner,
                                                                                            BookingStatus bookingStatus,
                                                                                            LocalDate now,
                                                                                            LocalDate now1,
                                                                                            Pageable pageRequest);

    Booking findFirstBookingByItemAndStatusAndStartAfterOrderByStart(Item item,
                                                                     BookingStatus bookingStatus,
                                                                     LocalDate now);

    Booking findFirstBookingByItemAndStatusAndEndBeforeOrderByEndDesc(Item item,
                                                                      BookingStatus bookingStatus,
                                                                      LocalDate now);

    Booking findFirstBookingByBookerAndStatusAndEndBefore(User user,
                                                          BookingStatus bookingStatus,
                                                          LocalDate now);
}
