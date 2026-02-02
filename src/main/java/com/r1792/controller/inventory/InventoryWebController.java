package com.r1792.controller.inventory;

import com.r1792.service.inventory.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryWebController {

    private final InventoryService service;

    // ITEMS LIST PAGE
    @GetMapping("/items/view")
    public String viewItems(Model model) {
        model.addAttribute("items", service.listAllItems());
        return "inventory/items";   // templates/inventory/items.html
    }

    // LOCATIONS LIST PAGE
    @GetMapping("/locations/view")
    public String viewLocations(Model model) {
        model.addAttribute("locations", service.listAllLocations());
        return "inventory/locations"; // templates/inventory/locations.html
    }

    // STOCK BALANCE PAGE
    @GetMapping("/stock/view")
    public String viewStock(Model model) {
        model.addAttribute("balances", service.listAllBalances());
        return "inventory/stock"; // templates/inventory/stock.html
    }

    // RECEIVE STOCK FORM
    @GetMapping("/receive")
    public String receiveStockPage(Model model) {
        model.addAttribute("items", service.listAllItems());
        model.addAttribute("locations", service.listAllLocations());
        return "inventory/receive";
    }
}