package com.r1792.repository;

import com.r1792.model.Battery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BatteryRepository extends JpaRepository<Battery, Long> {
    Optional<Battery> findBySerialNumber(String serialNumber);
}