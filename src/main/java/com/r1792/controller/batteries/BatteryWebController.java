package com.r1792.controller.batteries;

//import com.fasterxml.jackson.databind.ObjectMapper;
import com.r1792.model.batteries.Battery;
import com.r1792.model.batteries.BatteryUsage;
import com.r1792.service.batteries.BatteryService;
import com.r1792.service.batteries.BatteryUsageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.r1792.model.batteries.BatteryTest;
import tools.jackson.databind.ObjectMapper;

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
        return "batteries/batteries";
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

        return "batteries/battery-details"; // goes to templates/battery-details.html
    }



    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("battery", new Battery());
        return "batteries/battery-form";
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
        return "batteries/battery-form"; // same form, pre-filled
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
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        IO.println("Deleting Battery id: " + id);
        service.delete(id);
        return "redirect:/batteries";
    }
}

