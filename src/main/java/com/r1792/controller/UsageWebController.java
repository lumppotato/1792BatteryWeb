package com.r1792.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.r1792.model.Battery;
import com.r1792.model.BatteryUsage;
import com.r1792.service.BatteryService;
import com.r1792.service.BatteryUsageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/usage")
public class UsageWebController {
    private final BatteryUsageService usageService;
    private final BatteryService batteryService;

    public UsageWebController(BatteryUsageService usageService, BatteryService batteryService) {
        this.usageService = usageService;
        this.batteryService = batteryService;
    }

    // Show usage logs for ALL batteries
    @GetMapping
    public String listAll(Model model) {
        List<BatteryUsage> logs = usageService.getAll();
        model.addAttribute("logs", logs);
        return "usage-list";
    }

    // Show usage logs for a specific battery
    @GetMapping("/battery/{batteryId}")
    public String listForBattery(@PathVariable Long batteryId, Model model) {
        Battery battery = batteryService.get(batteryId);
        List<BatteryUsage> logs = usageService.getByBattery(batteryId);
        model.addAttribute("battery", battery);
        model.addAttribute("logs", logs);
        return "usage-list";
    }
    // Show add form with battery dropdown
    @GetMapping("/add")
    public String showGenericAddForm(Model model) {
        model.addAttribute("usage", new BatteryUsage());
        model.addAttribute("batteries", batteryService.getAll());
        return "usage-form";
    }
    // Add new usage log form
    @GetMapping("/add/{batteryId}")
    public String showAddForm(@PathVariable Long batteryId, Model model) {
        BatteryUsage usage = new BatteryUsage();
        usage.setBattery(batteryService.get(batteryId));
        model.addAttribute("usage", usage);
        return "usage-form";
    }

    // Save usage log
    @PostMapping("/add")
    public String save(@ModelAttribute BatteryUsage usage) {
        usageService.save(usage);
        return "redirect:/usage/battery/" + usage.getBattery().getId();
    }

    // Delete usage log
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        Long batteryId = usageService.get(id).getBattery().getId();
        usageService.delete(id);
        return "redirect:/usage/battery/" + batteryId;
    }

}
