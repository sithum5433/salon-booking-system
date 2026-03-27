package com.salon.booking.service;

import com.salon.booking.model.ClosedDate;
import com.salon.booking.model.SalonSetting;
import com.salon.booking.model.enums.SalonState;
import com.salon.booking.repository.ClosedDateRepository;
import com.salon.booking.repository.SalonSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalonService {

    private final ClosedDateRepository closedDateRepository;
    private final SalonSettingRepository salonSettingRepository;

    public SalonState getSalonState() {
        return salonSettingRepository.findAll().stream()
                .findFirst()
                .map(SalonSetting::getState)
                .orElse(SalonState.OPEN);
    }

    public void setSalonState(SalonState state) {
        SalonSetting setting = salonSettingRepository.findAll().stream()
                .findFirst()
                .orElse(SalonSetting.builder().state(SalonState.OPEN).build());

        setting.setState(state);
        salonSettingRepository.save(setting);
    }

    public boolean isClosedDate(LocalDate date) {
        return closedDateRepository.existsByClosedDate(date);
    }

    public List<ClosedDate> getAllClosedDates() {
        return closedDateRepository.findAll().stream()
                .sorted(Comparator.comparing(ClosedDate::getClosedDate))
                .toList();
    }

    public void addClosedDate(LocalDate closedDate, String reason) {
        if (!closedDateRepository.existsByClosedDate(closedDate)) {
            closedDateRepository.save(ClosedDate.builder()
                    .closedDate(closedDate)
                    .reason(reason)
                    .build());
        }
    }

    public void removeClosedDate(Long id) {
        closedDateRepository.deleteById(id);
    }
}
