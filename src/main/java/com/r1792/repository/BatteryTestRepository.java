package com.r1792.repository;

import com.r1792.model.BatteryTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BatteryTestRepository extends JpaRepository<BatteryTest, Long> {
    List<BatteryTest> findByBatteryId(Long batteryId);
    BatteryTest findTopByBatteryIdAndTestDateLessThanEqualOrderByTestDateDesc(
            Long battery_id, LocalDate testDate);
}
