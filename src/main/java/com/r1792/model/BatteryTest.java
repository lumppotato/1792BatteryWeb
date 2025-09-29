package com.r1792.model;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "battery_tests")
public class BatteryTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "battery_id", nullable = false)
    private Battery battery;
    @Column (name = "test_date")
    private LocalDate testDate = LocalDate.now();
    @Column (name = "voltage")
    private Double voltage;
    @Column (name = "internal_resistance")
    private Double internalResistance;
    @Column (name = "temperature")
    private Double temperature;
    @Column (name = "tester")
    private String tester;
    @Column(name = "1v1")
    private double oneV1; // 1 volt 1 amp
    @Column(name = "18v2")
    private double eighteenV2; // 18v at 2 amps
    @Column (name = "amp_hours")
    private Integer ampHours;
    @Column (name = "rated_capacity")
    private Double ratedCapacity;
    @Column (name = "tested_capacity")
    private Double testedCapacity;
    @Column (name = "test_time")
    private String testTime;
    @Column (name = "pass_test")
    private Boolean pass;
    @Enumerated(EnumType.STRING)
    private TestType testType;

    @Column(columnDefinition = "JSON")
    private String points;

    // getters and setters
    public enum TestType {LONG, SHORT};
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Battery getBattery() {
        return battery;
    }

    public void setBattery(Battery battery) {
        this.battery = battery;
    }

    public LocalDate getTestDate() {
        return testDate;
    }

    public void setTestDate(LocalDate testDate) {
        this.testDate = testDate;
    }

    public Double getVoltage() {
        return voltage;
    }

    public void setVoltage(Double voltage) {
        this.voltage = voltage;
    }

    public Double getInternalResistance() {
        return internalResistance;
    }

    public void setInternalResistance(Double internalResistance) {
        this.internalResistance = internalResistance;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public String getTester() {
        return tester;
    }

    public void setTester(String tester) {
        this.tester = tester;
    }

    public double getOneV1() {
        return oneV1;
    }

    public void setOneV1(double oneV1) {
        this.oneV1 = oneV1;
    }

    public double getEighteenV2() {
        return eighteenV2;
    }

    public void setEighteenV2(double eighteenV2) {
        this.eighteenV2 = eighteenV2;
    }

    public Integer getAmpHours() {
        return ampHours;
    }

    public void setAmpHours(Integer ampHours) {
        this.ampHours = ampHours;
    }

    public Double getRatedCapacity() {
        return ratedCapacity;
    }

    public void setRatedCapacity(Double ratedCapacity) {
        this.ratedCapacity = ratedCapacity;
    }

    public Double getTestedCapacity() {
        return testedCapacity;
    }

    public void setTestedCapacity(Double testedCapacity) {
        this.testedCapacity = testedCapacity;
    }

    public String getTestTime() {
        return testTime;
    }

    public void setTestTime(String testTime) {
        this.testTime = testTime;
    }

    public Boolean getPass() {
        return pass;
    }

    public void setPass(Boolean pass) {
        this.pass = pass;
    }

    public TestType getTestType() {
        return testType;
    }

    public void setTestType(TestType testType) {
        this.testType = testType;
    }

    public String getPoints() {
        return points;
    }
    public void setPoints(String points) {
        this.points = points;
    }
}
