package com.r1792.controller.inventory;

import com.r1792.model.inventory.InventoryBalance;
import com.r1792.model.inventory.Item;
import com.r1792.model.inventory.Location;
import com.r1792.service.inventory.InventoryService;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }

    @GetMapping("/items")
    public List<Item> listItems() {
        return service.listAllItems();
    }


    @PostMapping("/items")
    public Item createItem(@RequestBody Item item) {
        return service.createOrUpdateItem(item);
    }

    @GetMapping("/locations")
    public List<Location> listLocations() {
        return service.listAllLocations();
    }

    @PostMapping("/locations")
    public Location createLocation(@RequestBody Location loc) {
        return service.createOrUpdateLocation(loc);
    }

    @GetMapping("/balances")
    public List<InventoryBalance> listBalances() {
        return service.listAllBalances();
    }

    @PostMapping("/receive")
    public InventoryBalance receive(@RequestParam String itemCode,
                                    @RequestParam String locationCode,
                                    @RequestParam double qty) {
        return service.receive(itemCode, locationCode, qty);
    }

    @PostMapping("/build")
    public String buildAssembly(@RequestParam String parentItemCode,
                                @RequestParam int qty,
                                @RequestParam String consumeLocation,
                                @RequestParam String finishedLocation) {
        service.buildAssembly(parentItemCode, qty, consumeLocation, finishedLocation);
        return "Built " + qty + " of " + parentItemCode;
    }
}