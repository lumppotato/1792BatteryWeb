package com.r1792.dto.onshape;

import com.r1792.model.inventory.Item;
import com.r1792.model.onshape.OnshapeBomItem;

public class BomInventoryRow {

    private OnshapeBomItem bomItem;
    private Item item;          // null if no match
    private Double onHandQty;   // optional, fill later
    private Double toBuyQty;    // optional, fill later

    public BomInventoryRow(OnshapeBomItem bomItem, Item item,
                           Double onHandQty, Double toBuyQty) {
        this.bomItem = bomItem;
        this.item = item;
        this.onHandQty = onHandQty;
        this.toBuyQty = toBuyQty;
    }

    public OnshapeBomItem getBomItem() { return bomItem; }
    public Item getItem() { return item; }
    public Double getOnHandQty() { return onHandQty; }
    public Double getToBuyQty() { return toBuyQty; }
}