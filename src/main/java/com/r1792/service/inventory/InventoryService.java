package com.r1792.service.inventory;

import com.r1792.model.inventory.*;
import com.r1792.repository.inventory.BomHeaderRepository;
import com.r1792.repository.inventory.InventoryBalanceRepository;
import com.r1792.repository.inventory.ItemRepository;
import com.r1792.repository.inventory.LocationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryService {

    private final ItemRepository itemRepo;
    private final LocationRepository locationRepo;
    private final InventoryBalanceRepository balanceRepo;
    private final BomHeaderRepository bomRepo;

    public InventoryService(ItemRepository itemRepo,
                            LocationRepository locationRepo,
                            InventoryBalanceRepository balanceRepo,
                            BomHeaderRepository bomRepo) {
        this.itemRepo = itemRepo;
        this.locationRepo = locationRepo;
        this.balanceRepo = balanceRepo;
        this.bomRepo = bomRepo;
    }

    @Transactional
    public Item createOrUpdateItem(Item item) {
        return itemRepo.save(item);
    }

    @Transactional
    public Location createOrUpdateLocation(Location loc) {
        return locationRepo.save(loc);
    }

    @Transactional
    public InventoryBalance adjustStock(String itemCode, String locationCode, double deltaQty) {
        Item item = itemRepo.findByItemCode(itemCode)
                .orElseThrow(() -> new IllegalArgumentException("Unknown itemCode: " + itemCode));

        Location location = locationRepo.findByCode(locationCode)
                .orElseThrow(() -> new IllegalArgumentException("Unknown location: " + locationCode));

        InventoryBalance bal = balanceRepo.findByItemAndLocation(item, location)
                .orElseGet(() -> new InventoryBalance(item, location));

        bal.setOnHandQty(bal.getOnHandQty() + deltaQty);
        return balanceRepo.save(bal);
    }

    /**
     * Receive purchased items.
     */
    @Transactional
    public InventoryBalance receive(String itemCode, String locationCode, double qty) {
        if (qty <= 0) {
            throw new IllegalArgumentException("Receive qty must be positive");
        }
        return adjustStock(itemCode, locationCode, qty);
    }

    /**
     * Build N assemblies of a manufactured or kit item.
     * Consumes components from a location and adds finished goods to another location.
     */
    @Transactional
    public void buildAssembly(String parentItemCode,
                              int buildQty,
                              String consumeLocationCode,
                              String finishedLocationCode) {

        if (buildQty <= 0) {
            throw new IllegalArgumentException("buildQty must be > 0");
        }

        Item parent = itemRepo.findByItemCode(parentItemCode)
                .orElseThrow(() -> new IllegalArgumentException("Unknown parent item: " + parentItemCode));

        BomHeader bom = bomRepo.findByParentItemAndActiveTrue(parent)
                .orElseThrow(() -> new IllegalStateException("No active BOM for " + parentItemCode));

        Location consumeLoc = locationRepo.findByCode(consumeLocationCode)
                .orElseThrow(() -> new IllegalArgumentException("Unknown location: " + consumeLocationCode));

        Location finishedLoc = locationRepo.findByCode(finishedLocationCode)
                .orElseThrow(() -> new IllegalArgumentException("Unknown location: " + finishedLocationCode));

        // 1) Consume components
        for (BomLine line : bom.getLines()) {
            Item comp = line.getComponentItem();
            double required = line.getQuantityPer() * buildQty;

            InventoryBalance bal = balanceRepo.findByItemAndLocation(comp, consumeLoc)
                    .orElseThrow(() -> new IllegalStateException(
                            "No stock for component " + comp.getItemCode() + " at " + consumeLoc.getCode()));

            if (bal.getOnHandQty() < required) {
                throw new IllegalStateException("Not enough stock of " + comp.getItemCode()
                        + " at " + consumeLoc.getCode()
                        + " required=" + required + " onHand=" + bal.getOnHandQty());
            }

            bal.setOnHandQty(bal.getOnHandQty() - required);
            balanceRepo.save(bal);
        }

        // 2) Add finished goods
        InventoryBalance fgBal = balanceRepo.findByItemAndLocation(parent, finishedLoc)
                .orElseGet(() -> new InventoryBalance(parent, finishedLoc));

        fgBal.setOnHandQty(fgBal.getOnHandQty() + buildQty);
        balanceRepo.save(fgBal);
    }

    public List<Item> listAllItems() {
        return itemRepo.findAll();
    }

    public List<Location> listAllLocations() {
        return locationRepo.findAll();
    }

    public List<InventoryBalance> listAllBalances() {
        return balanceRepo.findAll();
    }
}
