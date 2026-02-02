package com.r1792.controller.onshape;

import com.r1792.model.onshape.OnshapeBomItem;
import com.r1792.service.onshape.OnshapeBomService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/onshape")
public class OnshapeTestController {

    private final OnshapeBomService bomService;

    public OnshapeTestController(OnshapeBomService bomService) {
        this.bomService = bomService;
    }

    // GET /api/onshape/bom?did=...&wid=...&eid=...
    @GetMapping("/bom")
    public List<OnshapeBomItem> getBom(
            @RequestParam String did,
            @RequestParam String wid,
            @RequestParam String eid
    ) throws Exception {
        return bomService.getAggregatedBom(did, wid, eid);
    }
}
