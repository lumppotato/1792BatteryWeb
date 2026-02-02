package com.r1792.model.inventory;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "inv_bom_headers")
public class BomHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Parent assembly/kit
    @ManyToOne(optional = false)
    @JoinColumn(name = "parent_item_id")
    private Item parentItem;

    @Column(length = 32)
    private String revision;

    @Column(nullable = false)
    private boolean active = true;

    @OneToMany(mappedBy = "bom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BomLine> lines = new ArrayList<>();

    public BomHeader() {
    }

    public BomHeader(Item parentItem, String revision) {
        this.parentItem = parentItem;
        this.revision = revision;
    }

    // getters / setters

    public Long getId() {
        return id;
    }

    public Item getParentItem() {
        return parentItem;
    }

    public void setParentItem(Item parentItem) {
        this.parentItem = parentItem;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<BomLine> getLines() {
        return lines;
    }

    public void setLines(List<BomLine> lines) {
        this.lines = lines;
    }
}