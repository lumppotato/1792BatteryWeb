package com.r1792.controller.onshape;

import com.r1792.model.onshape.BomItem;
import com.r1792.model.onshape.OnshapeBomForm;

import com.r1792.model.onshape.OnshapeBomItem;
import com.r1792.service.onshape.OnshapeBomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/onshape")
public class OnshapeWebController {

    private final OnshapeBomService bomService;

    public OnshapeWebController(OnshapeBomService bomService) {
        this.bomService = bomService;
    }

    // Show the form
    @GetMapping("/import-bom")
    public String showImportBomForm(Model model) {
        OnshapeBomForm form = new OnshapeBomForm();

        // Optional: pre-fill with your known good IDs
        // form.setDocumentId("fbebb0acbdab86ae66b65cd0");
        // form.setWorkspaceId("0b0236da5ddf3e907aa57778");
        // form.setElementId("5fa66501089322bc3d5d6a1c");

        model.addAttribute("bomForm", form);
        return "onshape/import-bom";
    }

    // Handle the POST + call the service
    @PostMapping("/import-bom")
    public String handleImportBom(@ModelAttribute("bomForm") OnshapeBomForm form,
                                  Model model) {
        try {
            List<OnshapeBomItem> bom = bomService.getAggregatedBom(
                    form.getDocumentId(),
                    form.getWorkspaceId(),
                    form.getElementId()
            );

            model.addAttribute("bomItems", bom);
            model.addAttribute("errorMessage", null);
        } catch (Exception ex) {
            ex.printStackTrace();
            model.addAttribute("bomItems", null);
            model.addAttribute("errorMessage", "Failed to fetch BOM: " + ex.getMessage());
        }

        // Reuse same page to show results underneath the form
        return "onshape/import-bom";
    }

    // simple stub for your “Import Items” menu entry
    @GetMapping("/import-items")
    public String showImportItems() {
        return "onshape/import-items";
    }
}