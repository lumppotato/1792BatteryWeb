package com.r1792.model.inventory;
import jakarta.persistence.*;

@Entity
@Table(
        name = "inv_balances",
        uniqueConstraints = @UniqueConstraint(columnNames = {"item_id", "location_id"})
)
public class InventoryBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "on_hand_qty", nullable = false)
    private double onHandQty = 0.0;

    @Column(name = "reserved_qty", nullable = false)
    private double reservedQty = 0.0;

    public InventoryBalance() {
    }

    public InventoryBalance(Item item, Location location) {
        this.item = item;
        this.location = location;
    }

    // getters / setters

    public Long getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public double getOnHandQty() {
        return onHandQty;
    }

    public void setOnHandQty(double onHandQty) {
        this.onHandQty = onHandQty;
    }

    public double getReservedQty() {
        return reservedQty;
    }

    public void setReservedQty(double reservedQty) {
        this.reservedQty = reservedQty;
    }
}