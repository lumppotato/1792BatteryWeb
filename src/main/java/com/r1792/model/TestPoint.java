package com.r1792.model;

public class TestPoint {
    private double time;
    private double voltage;
    private double current;

    public TestPoint(double time, double voltage, double current) {
        this.time = time;
        this.voltage = voltage;
        this.current = current;
    }


    public double getTime() { return time; }
    public double getVoltage() { return voltage; }
    public double getCurrent() { return current; }
}