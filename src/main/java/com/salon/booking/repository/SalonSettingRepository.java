package com.salon.booking.repository;

import com.salon.booking.model.SalonSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalonSettingRepository extends JpaRepository<SalonSetting, Long> {
}
