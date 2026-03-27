package com.salon.booking.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequest {

    @NotBlank(message = "Name is required.")
    private String customerName;

    @NotBlank(message = "Email is required.")
    @Email(message = "Enter a valid email.")
    private String email;

    @Min(value = 1, message = "Stars must be between 1 and 5.")
    @Max(value = 5, message = "Stars must be between 1 and 5.")
    private int stars;

    @NotBlank(message = "Review comment is required.")
    private String comment;
}
