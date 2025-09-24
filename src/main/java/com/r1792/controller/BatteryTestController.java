package com.r1792.controller;

import com.r1792.model.Battery;
import com.r1792.model.BatteryTest;
import com.r1792.service.BatteryService;
import com.r1792.service.BatteryTestCompressor;
import com.r1792.service.BatteryTestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Controller
@RequestMapping("/battery-tests")
public class BatteryTestController {

    private final BatteryTestService testService;
    private final BatteryService batteryService;
    private final BatteryTestCompressor compressor = new BatteryTestCompressor();

    public BatteryTestController(BatteryTestService testService, BatteryService batteryService) {
        this.testService = testService;
        this.batteryService = batteryService;
    }

    @GetMapping
    public String listTests(@PathVariable Long batteryId, Model model) {
        Battery battery = batteryService.get(batteryId);
        model.addAttribute("battery", battery);
        model.addAttribute("tests", testService.getTestsForBattery(batteryId));
        return "battery-tests"; // thymeleaf page
    }

    @GetMapping("/add")
    public String showAddForm(@PathVariable Long batteryId, Model model) {
        BatteryTest test = new BatteryTest();
        test.setBattery(batteryService.get(batteryId));
        model.addAttribute("batteryTest", test);
        return "battery-test-form";
    }

    @PostMapping("/add")
    public String addTest(@PathVariable Long batteryId, @ModelAttribute BatteryTest batteryTest) {
        batteryTest.setBattery(batteryService.get(batteryId));
        testService.save(batteryTest);
        return "redirect:/batteries/" + batteryId + "/tests";
    }
    @GetMapping("/all")
    public String listAllTests(Model model) {
        model.addAttribute("tests", testService.getAll());
        return "all-battery-tests";
    }
    @GetMapping("/upload")
    public String uploadForm() {
        return "uploadTest"; // Thymeleaf template with file input
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        try {
            // Save file to temp dir
            Path temp = Files.createTempFile("battery_test", ".csv");
            Files.write(temp, file.getBytes());

            // Compress points
            List<BatteryTestCompressor.TestPoint> points = compressor.compressCsv(temp);

            // TODO: save points to database here
            model.addAttribute("points", points);
            model.addAttribute("count", points.size());

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "uploadResult"; // display results
    }


}
