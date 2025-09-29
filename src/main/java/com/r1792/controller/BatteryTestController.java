package com.r1792.controller;

import com.r1792.model.Battery;
import com.r1792.model.BatteryTest;
import com.r1792.model.TestPoint;
import com.r1792.repository.BatteryRepository;
import com.r1792.service.BatteryService;
import com.r1792.service.BatteryTestCompressor;
import com.r1792.service.BatteryTestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

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
    public String showAddForm(Model model) {
        model.addAttribute("test", new BatteryTest());
        model.addAttribute("batteries", batteryService.getAll()); // all batteries for dropdown
        return "battery-test-form";
    }

    @PostMapping("/addbattery")
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
        return "uploadTest"; // // long battery test upload
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        Path temp = Files.createTempFile("battery_test", ".csv");
        Files.write(temp, file.getBytes());   // Save file to temp



        //IO.println("Trying to read: " + temp);
        try (Reader reader = Files.newBufferedReader(temp);
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)){

        //Get Records
          //  IO.println("Attempting to get records");
            List<CSVRecord> records = csvParser.getRecords();



            String date          = records.get(1).get(0);
            String testTime      = records.get(1).get(2);  // (hh:mm:ss): 2:00:14
            String ratedCapacity = records.get(4).get(0);  // 18.00 Ah
            String testedCapacity= records.get(4).get(1);
            String passFail      = records.get(3).get(2);

            //Line 19 starts test B19 = Time, C19 = V, D19 = Current
            //debug lines
//            IO.println("Rated Capacity: " + ratedCapacity);
//            IO.println("Tested Capacity: " + testedCapacity);
//            IO.println("Pass Fail: " + passFail);
//            IO.println("Date: " + date);

            model.addAttribute("date", date);
            model.addAttribute("testTime", testTime);
            model.addAttribute("ratedCapacity", ratedCapacity);
            model.addAttribute("testedCapacity", testedCapacity);
            model.addAttribute("passFail", passFail);



            //Chart
            List<TestPoint> points = new ArrayList<>();
            double lastV = Double.NaN;
            double lastI = Double.NaN;

            for (int i = 16; i < records.size(); i++) {
                CSVRecord rec = records.get(i);

                // skip rows that are incomplete or empty
                if (rec.size() < 4 || rec.get(1).isBlank() || rec.get(2).isBlank() || rec.get(3).isBlank()) {
                    continue;
                }

                try {
                    double time    = Double.parseDouble(rec.get(1));
                    double voltage = Double.parseDouble(rec.get(2));
                    double current = Double.parseDouble(rec.get(3));

                    if (Double.isNaN(voltage) || Double.isNaN(current)) continue;

                    // only store if voltage/current changed
                    if (points.isEmpty() || voltage != lastV || current != lastI) {
                        points.add(new TestPoint(time, voltage, current));
                        lastV = voltage;
                        lastI = current;
                    }
                } catch (NumberFormatException e) {
                    IO.println("Skipping row " + i + ": " + e.getMessage());
                }
            }
            //model.addAttribute("points", points); // Pass the points in JSON no longer needed as passing with pointsJson
            ObjectMapper objectMapper = new ObjectMapper();
            String pointsJson = objectMapper.writeValueAsString(points);
            model.addAttribute("pointsJson", pointsJson); // for hidden form field
            model.addAttribute("batteries", batteryService.getAll()); // all batteries for dropdown
        } catch (Exception e) {
            IO.println("Error: " + e.getMessage());
            model.addAttribute("error", e.getMessage());
        }
        return "uploadResult"; // display results


    }

    @PostMapping("/batteries/tests/save")
    public String saveTest(@RequestParam Long batteryId,
                           @RequestParam String date,
                           @RequestParam String ratedCapacity,
                           @RequestParam String testedCapacity,
                           @RequestParam String passFail,
                           @RequestParam String points) {
        IO.println("Getting Battery");
        Battery battery = batteryService.get(batteryId);
        IO.println("Battery got - creating test");
        BatteryTest test = new BatteryTest();

        IO.println("Saving test for battery " + batteryId);
        test.setBattery(battery);
        IO.println("setting date");
        test.setTestDate(LocalDate.parse(date, DateTimeFormatter.ofPattern("MM/dd/yy")));
        IO.println("setting rated capacity");
        test.setRatedCapacity(parseNumber(ratedCapacity));
        IO.println("setting tested capacity");
        test.setTestedCapacity(parseNumber(testedCapacity));
        IO.println("setting pass/fail");
        test.setPass(Boolean.valueOf(passFail));
        IO.println("setting Json");
        test.setPoints(points); // JSON stored as string
        IO.println("setting test type");
        test.setTestType(BatteryTest.TestType.LONG);
        IO.println("Saving");
        testService.save(test);

        return "redirect:/batteries/" + batteryId; // show the battery page again
    }

    private Double parseNumber(String input) {
        if (input == null) return null;
        String cleaned = input.replaceAll("[^0-9.\\-]", ""); // keep digits, decimal, minus
        if (cleaned.isBlank()) return null;
        return Double.valueOf(cleaned);
    }
    }
