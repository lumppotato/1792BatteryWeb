package com.r1792.model.inventory;

import jakarta.persistence.*;

@Entity
@Table(name = "inv_bom_lines")
public class BomLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bom_id")
    private BomHeader bom;

    @ManyToOne(optional = false)
    @JoinColumn(name = "component_item_id")
    private Item componentItem;

    @Column(name = "qty_per", nullable = false)
    private double quantityPer;

    @Column(length = 1000)
    private String notes;

    public BomLine() {
    }

    public BomLine(BomHeader bom, Item componentItem, double quantityPer) {
        this.bom = bom;
        this.componentItem = componentItem;
        this.quantityPer = quantityPer;
    }

    // getters / setters

    public Long getId() {
        return id;
    }

    public BomHeader getBom() {
        return bom;
    }

    public void setBom(BomHeader bom) {
        this.bom = bom;
    }

    public Item getComponentItem() {
        return componentItem;
    }

    public void setComponentItem(Item componentItem) {
        this.componentItem = componentItem;
    }

    public double getQuantityPer() {
        return quantityPer;
    }

    public void setQuantityPer(double quantityPer) {
        this.quantityPer = quantityPer;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}