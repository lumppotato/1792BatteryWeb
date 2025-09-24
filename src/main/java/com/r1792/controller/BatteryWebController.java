package com.r1792.controller;

import com.r1792.model.Battery;
import com.r1792.service.BatteryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/batteries")
public class BatteryWebController {

    private final BatteryService service;

    public BatteryWebController(BatteryService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("batteries", service.getAll());
        return "batteries";
    }

    @GetMapping("/{id}")
    public String viewBattery(@PathVariable Long id, Model model) {
        Battery battery = service.get(id);
        model.addAttribute("battery", battery);
        model.addAttribute("tests", battery.getTests()); // if you mapped OneToMany
        return "battery-details";
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

