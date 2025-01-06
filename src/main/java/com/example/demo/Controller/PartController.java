package com.example.demo.Controller;

import com.example.demo.Domain.*;
import com.example.demo.Service.PartService.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
public class PartController {

    private PartService partService;
    private Order order;

    @Autowired
    public PartController(PartService partService, Order order) {
        this.partService = partService;
        this.order = order;
    }

    @GetMapping("/showPartFormForUpdate")
    public String showPartFormForUpdate(@RequestParam("partID") int partId, Model model){

        var curPart = partService.findById((long) partId);
        String partType, form;

        if (curPart.getClass().equals(InhousePart.class)) {
            partType = "inhousepart";
            form = "form/inhouse_part_form";
        } else {
            partType = "outsourcedpart";
            form = "form/outsourced_part_form";
        }

        model.addAttribute(partType, curPart);
        return form;
    }

    @GetMapping("/deletepart")
    public String deletePart(@Valid @RequestParam("partID") int partId){

        Part part = partService.findById((long) partId);
        if (order.getOrderItemSet()
                .stream()
                .anyMatch(orderItem -> orderItem.getItem().equals(part))) {
            return "error/error_item_in_order";
        }
        if(!part.getProducts().isEmpty()){
            return "error/error_not_enuf_parts";

        }

        partService.deleteById((long) partId);
        return "confirmation/confirmationdeletepart";
    }

    // inhouse
    @GetMapping("/showFormAddInPart")
    public String showFormAddInhousePart(Model model){

        model.addAttribute("inhousepart", new InhousePart());

        return "form/inhouse_part_form";
    }

    @PostMapping("/showFormAddInPart")
    public String submitForm(@Valid @ModelAttribute("inhousepart") InhousePart part, BindingResult theBindingResult, Model model){

        model.addAttribute("inhousepart",part);

        if(theBindingResult.hasErrors()){
            return "form/inhouse_part_form";
        }

        partService.save(part);
        return "confirmation/confirmationaddpart";
    }

    // outsourced
    @GetMapping("/showFormAddOutPart")
    public String showFormAddOutsourcedPart(Model model){

        model.addAttribute("outsourcedpart", new OutsourcedPart());

        return "form/outsourced_part_form";
    }

    @PostMapping("/showFormAddOutPart")
    public String submitForm(@Valid @ModelAttribute("outsourcedpart") OutsourcedPart part, BindingResult bindingResult, Model model){

        model.addAttribute("outsourcedpart",part);

        if(bindingResult.hasErrors()){
            return "form/outsourced_part_form";
        }

        partService.save(part);
        return "confirmation/confirmationaddpart";
    }

}
