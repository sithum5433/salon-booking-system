package com.salon.booking.service;

import com.salon.booking.dto.ReviewRequest;
import com.salon.booking.model.Review;
import com.salon.booking.model.enums.ReviewStatus;
import com.salon.booking.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Review submit(ReviewRequest request) {
        Review review = Review.builder()
                .customerName(request.getCustomerName())
                .email(request.getEmail())
                .stars(request.getStars())
                .comment(request.getComment())
                .status(ReviewStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        return reviewRepository.save(review);
    }

    public List<Review> getApproved() {
        return reviewRepository.findByStatusOrderByCreatedAtDesc(ReviewStatus.APPROVED);
    }

    public List<Review> getAll() {
        return reviewRepository.findAll().stream()
                .sorted(Comparator.comparing(Review::getCreatedAt).reversed())
                .toList();
    }

    public void approve(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Review not found."));
        review.setStatus(ReviewStatus.APPROVED);
        reviewRepository.save(review);
    }

    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }
}
