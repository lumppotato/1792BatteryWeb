package com.r1792.service;

import com.r1792.model.BatteryTest;
import com.r1792.repository.BatteryTestRepository;
import org.springframework.stereotype.Service;

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
}