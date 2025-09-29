package com.r1792.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.r1792.model.Battery;
import com.r1792.model.BatteryUsage;
import com.r1792.service.BatteryService;
import com.r1792.service.BatteryUsageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.r1792.model.BatteryTest;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/batteries")
public class BatteryWebController {

    private final BatteryService service;
    private final BatteryUsageService usageService;

    public BatteryWebController(BatteryService service, BatteryUsageService usageService) {
        this.service = service;
        this.usageService = usageService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("batteries", service.getAll());
        return "batteries";
    }

    @GetMapping("/{id}")
    public String viewBattery(@PathVariable Long id, Model model) throws Exception {
        Battery battery = service.get(id);
        model.addAttribute("battery", battery);

        // Tests from the battery (make sure your Battery entity has @OneToMany(mappedBy="battery"))
        List<BatteryTest> tests = battery.getTests();
        model.addAttribute("tests", tests);

        // Usage logs
        List<BatteryUsage> usageLogs = usageService.getByBattery(id);
        model.addAttribute("usageLogs", usageLogs);

        // make a simple DTO list with only id + date
        List<Map<String, Object>> testDtos = tests.stream()
                .map(t -> {
                    Map<String, Object> m = new HashMap<>();
                    m.put("id", t.getId());
                    m.put("testDate", t.getTestDate() != null ? t.getTestDate().toString() : "");
                    m.put("points", t.getPoints());
                    return m;
                })
                .toList();

        ObjectMapper mapper = new ObjectMapper();
        model.addAttribute("testsJson", mapper.writeValueAsString(testDtos));

        return "battery-details"; // goes to templates/battery-details.html
    }



    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("battery", new Battery());
        return "battery-form"; // battery-form.html
    }

    @PostMapping("/add")
    public String addBattery(@ModelAttribute Battery battery) {
        service.save(battery);
        return "redirect:/batteries";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Battery battery = service.get(id);
        model.addAttribute("battery", battery);
        return "battery-form"; // same form, pre-filled
    }

    @PostMapping("/edit/{id}")
    public String updateBattery(@PathVariable Long id, @ModelAttribute Battery battery) {
        battery.setId(id);
        service.save(battery);
        return "redirect:/batteries";
    }
    @PostMapping("/save")
    public String saveBattery(@ModelAttribute Battery battery) {
        service.save(battery);
        return "redirect:/batteries";
    }
}

