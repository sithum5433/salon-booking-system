package com.salon.booking.service;

import com.salon.booking.dto.PackageRequest;
import com.salon.booking.model.PackageItem;
import com.salon.booking.repository.PackageItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PackageService {

    private final PackageItemRepository packageItemRepository;

    public List<PackageItem> getAll() {
        return packageItemRepository.findAll();
    }

    public PackageItem findById(Long id) {
        return packageItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Selected package was not found."));
    }

    public PackageItem create(PackageRequest request) {
        PackageItem item = PackageItem.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .imageUrl(request.getImageUrl() == null || request.getImageUrl().isBlank() ? "/templates/img/haircut.jpg" : request.getImageUrl())
                .build();
        return packageItemRepository.save(item);
    }

    public void delete(Long id) {
        packageItemRepository.deleteById(id);
    }
}
