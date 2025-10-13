package com.r1792.repository;

import com.r1792.model.BatteryUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BatteryUsageRepository extends JpaRepository<BatteryUsage, Long> {
    List<BatteryUsage> findByBatteryId(Long batteryId);

    // Add query method to find by test ID
    List<BatteryUsage> findByTestId(Long testId);
}