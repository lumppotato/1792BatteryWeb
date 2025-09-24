package com.r1792.service;

import com.r1792.model.BatteryUsage;
import com.r1792.repository.BatteryUsageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatteryUsageService {

    private final BatteryUsageRepository repo;

    public BatteryUsageService(BatteryUsageRepository repo) {
        this.repo = repo;
    }

    public List<BatteryUsage> getByBattery(Long batteryId) {
        return repo.findByBatteryId(batteryId);
    }

    public BatteryUsage save(BatteryUsage usage) {
        return repo.save(usage);
    }
}