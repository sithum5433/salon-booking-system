package com.salon.booking.controller;

import com.salon.booking.service.PackageService;
import com.salon.booking.service.ReviewService;
import com.salon.booking.service.SalonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PackageService packageService;
    private final ReviewService reviewService;
    private final SalonService salonService;

    @Value("${app.salon.name:Royal Salon}")
    private String salonName;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("salonName", salonName);
        model.addAttribute("packages", packageService.getAll());
        model.addAttribute("reviews", reviewService.getApproved());
        model.addAttribute("salonState", salonService.getSalonState());
        return "index";
    }
}
