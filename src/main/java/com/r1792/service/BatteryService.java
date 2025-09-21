package com.r1792.service;

import com.r1792.model.Battery;
import com.r1792.repository.BatteryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BatteryService {

    private final BatteryRepository repo;

    public BatteryService(BatteryRepository repo) {
        this.repo = repo;
    }

    public List<Battery> getAll() { return repo.findAll(); }
    public Battery save(Battery b) { return repo.save(b); }
    public Battery get(Long id) { return repo.findById(id).orElseThrow(); }
    public void delete(Long id) { repo.deleteById(id); }

}
