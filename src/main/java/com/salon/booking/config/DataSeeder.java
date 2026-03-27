package com.salon.booking.config;

import com.salon.booking.model.AdminUser;
import com.salon.booking.model.PackageItem;
import com.salon.booking.model.SalonSetting;
import com.salon.booking.model.enums.SalonState;
import com.salon.booking.repository.AdminUserRepository;
import com.salon.booking.repository.PackageItemRepository;
import com.salon.booking.repository.SalonSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final AdminUserRepository adminUserRepository;
    private final PackageItemRepository packageItemRepository;
    private final SalonSettingRepository salonSettingRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (adminUserRepository.count() == 0) {
            adminUserRepository.save(AdminUser.builder()
                    .fullName("Owner Admin")
                    .username("owner")
                    .password(passwordEncoder.encode("owner123"))
                    .role("OWNER_ADMIN")
                    .build());

            adminUserRepository.save(AdminUser.builder()
                    .fullName("Developer Admin")
                    .username("developer")
                    .password(passwordEncoder.encode("developer123"))
                    .role("DEVELOPER_ADMIN")
                    .build());
        }

        if (packageItemRepository.count() == 0) {
            packageItemRepository.save(PackageItem.builder()
                    .name("Hair Cut")
                    .description("Professional modern haircut for a stylish look.")
                    .price(new BigDecimal("1500.00"))
                    .imageUrl("/templates/img/haircut.jpg")
                    .build());

            packageItemRepository.save(PackageItem.builder()
                    .name("Beard Cut")
                    .description("Clean beard trimming and shaping by skilled staff.")
                    .price(new BigDecimal("1000.00"))
                    .imageUrl("/templates/img/beard.jpg")
                    .build());

            packageItemRepository.save(PackageItem.builder()
                    .name("Hair + Beard Combo")
                    .description("Complete grooming package with haircut and beard styling.")
                    .price(new BigDecimal("2200.00"))
                    .imageUrl("/templates/img/shop.jpg")
                    .build());
        }

        if (salonSettingRepository.count() == 0) {
            salonSettingRepository.save(SalonSetting.builder()
                    .state(SalonState.OPEN)
                    .build());
        }
    }
}
