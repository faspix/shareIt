package com.shareit.booking.repository;

import com.shareit.booking.dto.ResponseBookingDto;
import com.shareit.booking.model.Booking;
import com.shareit.booking.utility.BookingStatus;
import com.shareit.item.dto.ResponseItemDto;
import com.shareit.item.model.Item;
import com.shareit.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    String CheckItemAvailableForBookingSQL = "SELECT b FROM Booking b " +
            "WHERE b.item = :item " +
            "AND b.status = 'APPROVED' " +
            "AND ((b.start BETWEEN :startDate AND :endDate) OR (b.end BETWEEN :startDate AND :endDate))";

    @Query(CheckItemAvailableForBookingSQL)
    List<Booking> CheckItemAvailableForBooking(
            @Param("item") Item item,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);



    List<Booking> getBookingsByBookerOrderByStartDesc(User booker);

    List<Booking> getBookingsByBookerAndStatusOrderByStartDesc(User booker,
                                                               BookingStatus bookingStatus);

    List<Booking> getBookingsByBookerAndStatusAndStartIsAfterOrderByStartDesc(User booker,
                                                                              BookingStatus bookingStatus,
                                                                              LocalDate now);

    List<Booking> getBookingsByBookerAndStatusAndEndIsBeforeOrderByStartDesc(User booker,
                                                                             BookingStatus bookingStatus,
                                                                             LocalDate now);

    List<Booking> getBookingsByBookerAndStatusAndStartIsBeforeAndEndIsAfterOrderByStartDesc(User booker,
                                                                                            BookingStatus bookingStatus,
                                                                                            LocalDate now,
                                                                                            LocalDate now1);

    List<Booking> findBookingsByItemOwnerOrderByStart(User owner);

    List<Booking> findBookingsByItemOwnerAndStatusOrderByStart(User owner,
                                                               BookingStatus bookingStatus);

    List<Booking> findBookingsByItemOwnerAndStatusAndEndIsBeforeOrderByStart(User owner,
                                                                             BookingStatus bookingStatus,
                                                                             LocalDate now);

    List<Booking> findBookingsByItemOwnerAndStatusAndStartIsAfterOrderByStart(User owner,
                                                                              BookingStatus bookingStatus,
                                                                              LocalDate now);

    List<Booking> findBookingsByItemOwnerAndStatusAndStartIsBeforeAndEndIsAfterOrderByStart(User owner,
                                                                                            BookingStatus bookingStatus,
                                                                                            LocalDate now,
                                                                                            LocalDate now1);



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
