package com.salon.booking.repository;

import com.salon.booking.model.ClosedDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ClosedDateRepository extends JpaRepository<ClosedDate, Long> {
    boolean existsByClosedDate(LocalDate closedDate);
}
