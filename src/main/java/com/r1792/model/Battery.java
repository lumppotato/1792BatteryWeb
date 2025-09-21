package com.r1792.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Entity
@Table(name = "batteries")
public class Battery {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial_number", unique = true, nullable = false)
    private String serialNumber;

    private String brand;
    private Integer capacitymAh;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Enumerated(EnumType.STRING)
    private Status status = Status.NEW;

    @Column(name = "entered_service")
    private LocalDate enteredService;

    @Lob
    private String usage;

    @Column(name = "rfid_tag")
    private Integer rfidTag;

    public enum Status { NEW, GOOD, DEAD, RETIRED }




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

    public LocalDate getEnteredService() { return enteredService; }
    public void setEnteredService(LocalDate enteredService) { this.enteredService = enteredService; }

    public String getUsage() { return usage; }
    public void setUsage(String usage) { this.usage = usage; }

    public Integer getRfidTag() { return rfidTag; }
    public void setRfidTag(Integer rfidTag) { this.rfidTag = rfidTag; }

}
