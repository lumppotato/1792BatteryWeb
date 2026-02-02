package com.r1792.repository.batteries;

import com.r1792.model.batteries.Battery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BatteryRepository extends JpaRepository<Battery, Long> {
    Optional<Battery> findBySerialNumber(String serialNumber);
}