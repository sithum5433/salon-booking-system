package com.salon.booking.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "closed_dates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClosedDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private LocalDate closedDate;

    private String reason;
}
