package com.salon.booking.repository;

import com.salon.booking.model.PackageItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageItemRepository extends JpaRepository<PackageItem, Long> {
}
