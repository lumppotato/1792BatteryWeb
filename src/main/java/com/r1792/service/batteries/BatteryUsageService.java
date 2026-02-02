package com.r1792.service.batteries;

import com.r1792.model.batteries.BatteryUsage;
import com.r1792.repository.batteries.BatteryUsageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatteryUsageService {

    private final BatteryUsageRepository repo;  // ✅ no static

    public BatteryUsageService(BatteryUsageRepository repo) {
        this.repo = repo;
    }

    public List<BatteryUsage> getByBattery(Long batteryId) {
        return repo.findByBatteryId(batteryId);
    }
    public List<BatteryUsage> getByTest(Long testId) {
        return repo.findByTestId(testId);
    }

    public BatteryUsage save(BatteryUsage usage) {
        return repo.save(usage);
    }

    public List<BatteryUsage> getAll() {
        return repo.findAll();
    }

    public BatteryUsage get(Long id) {
        return repo.findById(id).orElseThrow();
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
