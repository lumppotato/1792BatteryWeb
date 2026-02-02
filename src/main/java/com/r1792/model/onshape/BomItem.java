package com.r1792.model.onshape;

public class BomItem {
    private String partNumber;
    private String name;
    private double quantity;

    public BomItem() {}

    public BomItem(String partNumber, String name, double quantity) {
        this.partNumber = partNumber;
        this.name = name;
        this.quantity = quantity;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}