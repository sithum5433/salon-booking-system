package com.salon.booking.controller;

import com.salon.booking.dto.PackageRequest;
import com.salon.booking.model.enums.SalonState;
import com.salon.booking.service.BookingService;
import com.salon.booking.service.PackageService;
import com.salon.booking.service.ReviewService;
import com.salon.booking.service.SalonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final BookingService bookingService;
    private final PackageService packageService;
    private final ReviewService reviewService;
    private final SalonService salonService;

    @GetMapping("/login")
    public String loginPage() {
        return "admin-login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("pendingCount", bookingService.getPending().size());
        model.addAttribute("allBookings", bookingService.getAll());
        model.addAttribute("salonState", salonService.getSalonState());
        return "admin-dashboard";
    }

    @GetMapping("/bookings")
    public String bookings(Model model) {
        bookingService.markPendingSeen();
        model.addAttribute("bookings", bookingService.getAll());
        return "admin-bookings";
    }

    @PostMapping("/bookings/{id}/approve")
    public String approveBooking(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        bookingService.approve(id);
        redirectAttributes.addFlashAttribute("successMessage", "Booking approved successfully.");
        return "redirect:/admin/bookings";
    }

    @PostMapping("/bookings/{id}/delete")
    public String deleteBooking(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        bookingService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Booking deleted successfully.");
        return "redirect:/admin/bookings";
    }

    @GetMapping("/packages")
    public String packages(Model model) {
        model.addAttribute("packages", packageService.getAll());
        if (!model.containsAttribute("packageRequest")) {
            model.addAttribute("packageRequest", new PackageRequest());
        }
        return "admin-packages";
    }

    @PostMapping("/packages")
    public String createPackage(@Valid @ModelAttribute("packageRequest") PackageRequest packageRequest,
                                BindingResult bindingResult,
                                Model model,
                                RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("packages", packageService.getAll());
            return "admin-packages";
        }

        packageService.create(packageRequest);
        redirectAttributes.addFlashAttribute("successMessage", "Package added successfully.");
        return "redirect:/admin/packages";
    }

    @PostMapping("/packages/{id}/delete")
    public String deletePackage(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        packageService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Package deleted successfully.");
        return "redirect:/admin/packages";
    }

    @GetMapping("/reviews")
    public String reviews(Model model) {
        model.addAttribute("reviews", reviewService.getAll());
        return "admin-reviews";
    }

    @PostMapping("/reviews/{id}/approve")
    public String approveReview(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        reviewService.approve(id);
        redirectAttributes.addFlashAttribute("successMessage", "Review approved successfully.");
        return "redirect:/admin/reviews";
    }

    @PostMapping("/reviews/{id}/delete")
    public String deleteReview(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        reviewService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Review deleted successfully.");
        return "redirect:/admin/reviews";
    }

    @GetMapping("/closed-dates")
    public String closedDates(Model model) {
        model.addAttribute("closedDates", salonService.getAllClosedDates());
        model.addAttribute("salonState", salonService.getSalonState());
        return "admin-closed-dates";
    }

    @PostMapping("/closed-dates/add")
    public String addClosedDate(@RequestParam("closedDate") LocalDate closedDate,
                                @RequestParam(value = "reason", required = false) String reason,
                                RedirectAttributes redirectAttributes) {
        salonService.addClosedDate(closedDate, reason);
        redirectAttributes.addFlashAttribute("successMessage", "Closed date added successfully.");
        return "redirect:/admin/closed-dates";
    }

    @PostMapping("/closed-dates/{id}/delete")
    public String deleteClosedDate(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        salonService.removeClosedDate(id);
        redirectAttributes.addFlashAttribute("successMessage", "Closed date removed successfully.");
        return "redirect:/admin/closed-dates";
    }

    @PostMapping("/salon-state")
    public String updateSalonState(@RequestParam("state") SalonState state,
                                   RedirectAttributes redirectAttributes) {
        salonService.setSalonState(state);
        redirectAttributes.addFlashAttribute("successMessage", "Salon status updated successfully.");
        return "redirect:/admin/closed-dates";
    }
}
