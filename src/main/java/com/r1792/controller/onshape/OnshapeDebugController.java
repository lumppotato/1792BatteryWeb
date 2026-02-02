package com.r1792.controller.onshape;

import com.r1792.service.onshape.OnshapeBomService;
import com.r1792.model.onshape.OnshapeBomItem;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OnshapeDebugController {

    private final OnshapeBomService service;

    public OnshapeDebugController(OnshapeBomService service) {
        this.service = service;
    }

    @GetMapping("/onshape/bom-test")
    public String testBom() throws Exception {

        // Replace these with the IDs you know work from JS
        String did = "fbebb0acbdab86ae66b65cd0";
        String wid = "0b0236da5ddf3e907aa57778";
        String eid = "5fa66501089322bc3d5d6a1c";

        List<OnshapeBomItem> bom = service.getAggregatedBom(did, wid, eid);

        System.out.println("=== Aggregated BOM ===");
        bom.forEach(item ->
                System.out.println(item.getTotalQty() + "  " +
                        item.getPartNumber() + "  " +
                        item.getName())
        );

        return "Printed aggregated BOM to console (" + bom.size() + " items).";
    }
}