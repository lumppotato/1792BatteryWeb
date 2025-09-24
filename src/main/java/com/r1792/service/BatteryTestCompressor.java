package com.r1792.service;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class BatteryTestCompressor {

    public static class TestPoint {
        private int time;
        private double voltage;
        private double current;
        private double temp;

        public TestPoint(int time, double voltage, double current, double temp) {
            this.time = time;
            this.voltage = voltage;
            this.current = current;
            this.temp = temp;
        }

        public int getTime() { return time; }
        public double getVoltage() { return voltage; }
        public double getCurrent() { return current; }
        public double getTemp() { return temp; }
    }

    public List<TestPoint> compressCsv(Path csvPath) throws IOException {
        List<TestPoint> result = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(csvPath)) {
            String line;
            boolean firstLine = true;
            double lastVoltage = Double.NaN;

            while ((line = br.readLine()) != null) {
                if (firstLine) { firstLine = false; continue; } // skip header
                String[] parts = line.split(",");
                if (parts.length < 5) continue;

                int time = Integer.parseInt(parts[1].trim());
                double voltage = Double.parseDouble(parts[2].trim());
                double current = Double.parseDouble(parts[3].trim());
                double temp = Double.parseDouble(parts[4].trim());

                // Always keep first, or when voltage changes
                if (result.isEmpty() || Math.abs(voltage - lastVoltage) > 0.001) {
                    result.add(new TestPoint(time, voltage, current, temp));
                    lastVoltage = voltage;
                }
            }
        }
        return result;
    }
}