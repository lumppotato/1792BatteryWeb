package com.r1792.model;


import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "battery_usage")
public class BatteryUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "battery_id", nullable = false)
    private Battery battery;
    @Column(name ="robot_name")
    private String robotName;
    @Column(name ="event_name")
    private String eventName;
    @Column(name = "used_date")
    private LocalDateTime usedDate;
    @Column(name="run_time")
    private double runTime;




    public double getRunTime() {
        return runTime;
    }

    public void setRunTime(double runTime) {
        this.runTime = runTime;
    }

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

    public String getRobotName() {
        return robotName;
    }

    public void setRobotName(String robotName) {
        this.robotName = robotName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDateTime getUsedDate() {
        return usedDate;
    }

    public void setUsedDate(LocalDateTime usedDate) {
        this.usedDate = usedDate;
    }
}