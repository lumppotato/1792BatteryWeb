package com.r1792.controller;

import com.r1792.model.batteries.BatteryTest;
import com.r1792.service.batteries.BatteryTestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.r1792.model.batteries.BatteryUsage;
import com.r1792.service.batteries.BatteryService;
import com.r1792.service.batteries.BatteryUsageService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/usage")
public class UsageWebController {
    private final BatteryUsageService usageService;
    private final BatteryService batteryService;
    private final BatteryTestService testService;
    public UsageWebController(BatteryUsageService usageService,
                              BatteryService batteryService,
                              BatteryTestService testService) {
        this.usageService = usageService;
        this.batteryService = batteryService;
        this.testService = testService;
    }
    //test again once again


    // Show usage logs for ALL batteries
    @GetMapping
    public String listAll(Model model) {
        List<BatteryUsage> logs = usageService.getAll();

        model.addAttribute("logs", logs);
        return "batteries/usage-list";
    }

    // Show usage logs for a specific battery
    @GetMapping("/battery/{batteryId}")
    public String listForBattery(@PathVariable Long batteryId, Model model) {
        var battery = batteryService.get(batteryId);
        var logs = usageService.getByBattery(batteryId);

        Map<Long, BatteryTest> testsByUsage = new HashMap<>();
        for (var u : logs) {
            var t = testService.latestBeforeUsage(batteryId, u.getUsedDate()); // may be null
            testsByUsage.put(u.getId(), t);
        }

        model.addAttribute("battery", battery);
        model.addAttribute("logs", logs);
        model.addAttribute("testsByUsage", testsByUsage);
        return "batteries/usage-list";


    }
    // Show add form with battery dropdown
    @GetMapping("/add")
    public String showGenericAddForm(Model model) {
        model.addAttribute("usage", new BatteryUsage());
        model.addAttribute("batteries", batteryService.getAll());
        return "batteries/usage-form";
    }
    // usage log form
    @GetMapping("/add/{batteryId}")
    public String showAddForm(@PathVariable Long batteryId, Model model) {
        BatteryUsage usage = new BatteryUsage();
        usage.setBattery(batteryService.get(batteryId));
        model.addAttribute("usage", usage);
        return "batteries/usage-form";
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
