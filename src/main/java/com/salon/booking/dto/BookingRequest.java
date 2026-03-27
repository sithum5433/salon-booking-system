package com.salon.booking.dto;

import com.salon.booking.model.enums.DurationType;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class BookingRequest {

    @NotBlank(message = "Full name is required.")
    private String fullName;

    @NotBlank(message = "Mobile number is required.")
    private String mobileNumber;

    @NotBlank(message = "Email is required.")
    @Email(message = "Enter a valid email.")
    private String email;

    @NotNull(message = "Select a package.")
    private Long packageId;

    @NotNull(message = "Select a booking date.")
    @FutureOrPresent(message = "Booking date must be today or in the future.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate bookingDate;

    @NotNull(message = "Select a start time.")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @NotNull(message = "Select a duration.")
    private DurationType durationType;
}
