package com.r1792.model.inventory;

import jakarta.persistence.*;


@Entity
@Table(name = "inv_items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_code", unique = true, nullable = false, length = 64)
    private String itemCode;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_type", nullable = false)
    private ItemType itemType = ItemType.PURCHASED;

    @Column(name = "uom", length = 16)
    private String uom = "EA";

    @Column(length = 128)
    private String category;

    @Column(name = "preferred_vendor", length = 128)
    private String preferredVendor;

    @Column(name = "vendor_part_number", length = 128)
    private String vendorPartNumber;

    @Column(name = "reorder_point")
    private Double reorderPoint;

    @Column(name = "target_stock")
    private Double targetStock;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    public Item() {
    }

    public Item(String itemCode, String name, ItemType itemType) {
        this.itemCode = itemCode;
        this.name = name;
        this.itemType = itemType;
    }

    // --- getters and setters ---

    public Long getId() {
        return id;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPreferredVendor() {
        return preferredVendor;
    }

    public void setPreferredVendor(String preferredVendor) {
        this.preferredVendor = preferredVendor;
    }

    public String getVendorPartNumber() {
        return vendorPartNumber;
    }

    public void setVendorPartNumber(String vendorPartNumber) {
        this.vendorPartNumber = vendorPartNumber;
    }

    public Double getReorderPoint() {
        return reorderPoint;
    }

    public void setReorderPoint(Double reorderPoint) {
        this.reorderPoint = reorderPoint;
    }

    public Double getTargetStock() {
        return targetStock;
    }

    public void setTargetStock(Double targetStock) {
        this.targetStock = targetStock;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
