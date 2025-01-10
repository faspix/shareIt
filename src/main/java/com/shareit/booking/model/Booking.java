package com.shareit.booking.model;

import com.shareit.booking.utility.BookingStatus;
import com.shareit.item.dto.ResponseItemDto;
import com.shareit.item.model.Item;
import com.shareit.item.repository.ItemRepository;
import com.shareit.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @OneToOne
    @JoinColumn(name = "item_id", referencedColumnName = "item_id")
    private Item item;

    @OneToOne
    @JoinColumn(name = "booker_id", referencedColumnName = "user_id")
    private User booker;


    @Column(name = "start_date")
//    @Temporal(value = TemporalType.DATE)
    private LocalDate start;

    @Column(name = "end_date")
//    @Temporal(value = TemporalType.DATE)
    private LocalDate end;



}
