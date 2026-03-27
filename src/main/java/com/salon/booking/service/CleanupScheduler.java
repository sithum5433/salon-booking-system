package com.salon.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CleanupScheduler {

    private final BookingService bookingService;

    @Scheduled(cron = "0 59 23 * * *", zone = "Asia/Colombo")
    public void clearEndOfDayBookings() {
        bookingService.deleteTodayBookings();
    }
}
