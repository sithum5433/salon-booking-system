package com.salon.booking.model;

import com.salon.booking.model.enums.SalonState;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "salon_settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalonSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SalonState state;
}
