package com.r1792.service;

import com.r1792.model.BatteryTest;
import com.r1792.repository.BatteryTestRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BatteryTestService {
    private final BatteryTestRepository repo;

    public BatteryTestService(BatteryTestRepository repo) {
        this.repo = repo;
    }

    public List<BatteryTest> getTestsForBattery(Long batteryId) {
        return repo.findByBatteryId(batteryId);
    }

    public void save(BatteryTest test) {
        repo.save(test);
    }
    public List<BatteryTest> getAll() {
        return repo.findAll();
    }
    public BatteryTest latestBeforeUsage(Long batteryId, LocalDateTime usedAt) {
        return repo.findTopByBatteryIdAndTestDateLessThanEqualOrderByTestDateDesc(batteryId, LocalDate.from(usedAt));
    }
}