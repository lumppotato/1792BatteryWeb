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

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("battery", new Battery());
        return "battery-form";
    }

    @PostMapping
    public String create(@ModelAttribute Battery battery) {
        service.save(battery);
        return "redirect:/batteries";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("battery", service.get(id));
        return "battery-form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Battery battery) {
        battery.setId(id);
        service.save(battery);
        return "redirect:/batteries";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/batteries";
    }
}

