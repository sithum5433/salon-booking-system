package com.salon.booking.service;

import com.salon.booking.dto.BookingRequest;
import com.salon.booking.model.Booking;
import com.salon.booking.model.PackageItem;
import com.salon.booking.model.enums.BookingStatus;
import com.salon.booking.model.enums.SalonState;
import com.salon.booking.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final PackageService packageService;
    private final SalonService salonService;
    private final EmailService emailService;

    public Booking saveBooking(BookingRequest request) {
        if (salonService.getSalonState() == SalonState.CLOSED) {
            throw new IllegalStateException("Salon is currently closed.");
        }

        if (salonService.isClosedDate(request.getBookingDate())) {
            throw new IllegalStateException("Selected date is marked as closed.");
        }

        if (request.getBookingDate().isEqual(LocalDate.now(ZoneId.of("Asia/Colombo"))) &&
            request.getStartTime().isBefore(LocalTime.now(ZoneId.of("Asia/Colombo")))) {
            throw new IllegalStateException("Cannot book a past time for today.");
        }

        LocalTime endTime = request.getStartTime().plusMinutes(request.getDurationType().getMinutes());

        if (request.getStartTime().isBefore(LocalTime.of(9, 0)) || endTime.isAfter(LocalTime.of(19, 0))) {
            throw new IllegalStateException("Booking must be between 09:00 AM and 07:00 PM.");
        }

        List<Booking> bookingsOnDate = bookingRepository.findByBookingDateOrderByStartTimeAsc(request.getBookingDate());

        for (Booking existing : bookingsOnDate) {
            if (existing.getStatus() == BookingStatus.REJECTED) {
                continue;
            }

            boolean overlap = request.getStartTime().isBefore(existing.getEndTime())
                    && endTime.isAfter(existing.getStartTime());

            if (overlap) {
                throw new IllegalStateException("This date and time is already booked.");
            }
        }

        PackageItem selectedPackage = packageService.findById(request.getPackageId());

        Booking booking = Booking.builder()
                .fullName(request.getFullName())
                .mobileNumber(request.getMobileNumber())
                .email(request.getEmail())
                .packageItem(selectedPackage)
                .bookingDate(request.getBookingDate())
                .startTime(request.getStartTime())
                .endTime(endTime)
                .durationType(request.getDurationType())
                .status(BookingStatus.PENDING)
                .adminSeen(false)
                .createdAt(LocalDateTime.now())
                .build();

        Booking saved = bookingRepository.save(booking);

        emailService.trySend(
                saved.getEmail(),
                "Booking Request Received",
                "Dear " + saved.getFullName() + ",\n\n"
                        + "Your booking request has been submitted successfully and is waiting for admin approval.\n"
                        + "Date: " + saved.getBookingDate() + "\n"
                        + "Time: " + saved.getStartTime() + " - " + saved.getEndTime() + "\n"
                        + "Package: " + saved.getPackageItem().getName() + "\n\n"
                        + "Thank you."
        );

        return saved;
    }

    public List<Booking> getAll() {
        return bookingRepository.findAllByOrderByBookingDateAscStartTimeAsc();
    }

    public List<Booking> getPending() {
        return bookingRepository.findByStatusOrderByBookingDateAscStartTimeAsc(BookingStatus.PENDING);
    }

    public long getPendingCount() {
        return bookingRepository.countByStatusAndAdminSeenFalse(BookingStatus.PENDING);
    }

    public void markPendingSeen() {
        List<Booking> pending = getPending();
        pending.forEach(booking -> booking.setAdminSeen(true));
        bookingRepository.saveAll(pending);
    }

    public void approve(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found."));
        booking.setStatus(BookingStatus.APPROVED);
        booking.setAdminSeen(true);
        bookingRepository.save(booking);

        emailService.trySend(
                booking.getEmail(),
                "Booking Approved",
                "Dear " + booking.getFullName() + ",\n\n"
                        + "Your booking has been approved successfully.\n"
                        + "Date: " + booking.getBookingDate() + "\n"
                        + "Time: " + booking.getStartTime() + " - " + booking.getEndTime() + "\n"
                        + "Package: " + booking.getPackageItem().getName() + "\n\n"
                        + "See you soon."
        );
    }

    public void reject(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found."));
        booking.setStatus(BookingStatus.REJECTED);
        booking.setAdminSeen(true);
        bookingRepository.save(booking);

        emailService.trySend(
                booking.getEmail(),
                "Booking Rejected",
                "Dear " + booking.getFullName() + ",\n\n"
                        + "Your booking request was not approved. Please choose another time or date.\n\n"
                        + "Thank you."
        );
    }

    // Delete a booking by its ID
    public void delete(Long bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    public void deleteTodayBookings() {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Colombo"));
        List<Booking> todayBookings = bookingRepository.findByBookingDateOrderByStartTimeAsc(today);
        bookingRepository.deleteAll(todayBookings);
    }
}
