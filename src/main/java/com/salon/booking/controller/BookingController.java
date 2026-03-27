package com.salon.booking.controller;

import com.salon.booking.dto.BookingRequest;
import com.salon.booking.model.enums.DurationType;
import com.salon.booking.service.BookingService;
import com.salon.booking.service.PackageService;
import com.salon.booking.service.SalonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final PackageService packageService;
    private final SalonService salonService;

    @GetMapping("/booking")
    public String bookingPage(Model model) {
        if (!model.containsAttribute("bookingRequest")) {
            model.addAttribute("bookingRequest", new BookingRequest());
        }
        model.addAttribute("packages", packageService.getAll());
        model.addAttribute("durations", DurationType.values());
        model.addAttribute("closedDates", salonService.getAllClosedDates());
        model.addAttribute("salonState", salonService.getSalonState());
        model.addAttribute("existingBookings", bookingService.getAll());
        return "booking";
    }

    @PostMapping("/booking")
    public String saveBooking(@Valid @ModelAttribute("bookingRequest") BookingRequest bookingRequest,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("packages", packageService.getAll());
            model.addAttribute("durations", DurationType.values());
            model.addAttribute("closedDates", salonService.getAllClosedDates());
            model.addAttribute("salonState", salonService.getSalonState());
            model.addAttribute("existingBookings", bookingService.getAll());
            return "booking";
        }

        try {
            bookingService.saveBooking(bookingRequest);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Booking request submitted successfully. Waiting for admin approval.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            redirectAttributes.addFlashAttribute("bookingRequest", bookingRequest);
        }

        return "redirect:/booking";
    }
}
