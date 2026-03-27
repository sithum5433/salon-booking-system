package com.salon.booking.repository;

import com.salon.booking.model.Review;
import com.salon.booking.model.enums.ReviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByStatusOrderByCreatedAtDesc(ReviewStatus status);
}
