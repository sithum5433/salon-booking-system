package com.salon.booking.controller;

import com.salon.booking.dto.ReviewRequest;
import com.salon.booking.service.ReviewService;
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
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/reviews")
    public String reviewsPage(Model model) {
        if (!model.containsAttribute("reviewRequest")) {
            model.addAttribute("reviewRequest", new ReviewRequest());
        }
        model.addAttribute("reviews", reviewService.getApproved());
        return "reviews";
    }

    @PostMapping("/reviews")
    public String submitReview(@Valid @ModelAttribute("reviewRequest") ReviewRequest reviewRequest,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("reviews", reviewService.getApproved());
            return "reviews";
        }

        reviewService.submit(reviewRequest);
        redirectAttributes.addFlashAttribute("successMessage",
                "Review submitted successfully and waiting for admin approval.");
        return "redirect:/reviews";
    }
}
