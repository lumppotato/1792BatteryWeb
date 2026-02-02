package com.r1792.model.onshape;


public class OnshapeBomItem {
    private final String partNumber;
    private final String name;
    private final double totalQty;

    public OnshapeBomItem(String partNumber, String name, double totalQty) {
        this.partNumber = partNumber;
        this.name = name;
        this.totalQty = totalQty;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public String getName() {
        return name;
    }

    public double getTotalQty() {
        return totalQty;
    }
}
