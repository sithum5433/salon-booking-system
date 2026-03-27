package com.salon.booking.repository;

import com.salon.booking.model.Booking;
import com.salon.booking.model.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByBookingDateOrderByStartTimeAsc(LocalDate bookingDate);
    List<Booking> findByStatusOrderByBookingDateAscStartTimeAsc(BookingStatus status);
    List<Booking> findAllByOrderByBookingDateAscStartTimeAsc();
    long countByStatusAndAdminSeenFalse(BookingStatus status);
}
