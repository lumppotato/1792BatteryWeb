package com.r1792.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "batteries")
public class Battery {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial_number", unique = true, nullable = false)
    private String serialNumber;

    @Column(name = "brand")
    private String brand;
    @Column(name = "entered_service")
    private LocalDateTime enteredService;
    @Column(name = "capacity_mAh")
    private Integer capacitymAh;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Enumerated(EnumType.STRING)
    private Status status = Status.NEW;

    @OneToMany(mappedBy = "battery", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BatteryTest> tests = new ArrayList<>();
    @Lob
    private String usage;

    @Column(name = "rfid_tag")
    private Integer rfidTag;



    public enum Status { NEW, GOOD, DEAD, RETIRED }


    public void setEnteredService(LocalDateTime enteredService) {
        this.enteredService = enteredService;
    }

    public List<BatteryTest> getTests() {
        return tests;
    }

    public void setTests(List<BatteryTest> tests) {
        this.tests = tests;
    }

    @OneToMany(mappedBy = "battery", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BatteryUsage> usageLogs = new ArrayList<>();

    public List<BatteryUsage> getUsageLogs() {
        return usageLogs;
    }

    public void setUsageLogs(List<BatteryUsage> usageLogs) {
        this.usageLogs = usageLogs;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public Integer getCapacitymAh() { return capacitymAh; }
    public void setCapacitymAh(Integer capacitymAh) { this.capacitymAh = capacitymAh; }

    public LocalDate getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(LocalDate purchaseDate) { this.purchaseDate = purchaseDate; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public LocalDateTime getEnteredService() { return enteredService; }
    public void setEnteredService(LocalDate enteredService) { this.enteredService = enteredService.atStartOfDay(); }

    public String getUsage() { return usage; }
    public void setUsage(String usage) { this.usage = usage; }

    public Integer getRfidTag() { return rfidTag; }
    public void setRfidTag(Integer rfidTag) { this.rfidTag = rfidTag; }

    public String getRowClass() {
        if ("GOOD".equalsIgnoreCase(status.name())) {
            IO.println("GOOD");
            return "status-good";
        } else if ("DEAD".equalsIgnoreCase(status.name())) {
            IO.println("BAD");
            return "status-bad";
        } else if ("RETIRED".equalsIgnoreCase(status.name())) {
            IO.println("degraded");
            return "status-degraded";
        } else {
            IO.println("New Battery");
            return "status-new";
        }
    }

}
