package com.example.demo.Controller;

import com.example.demo.Domain.*;
import com.example.demo.Service.Implementation.UsingCRUDRepository.ItemServiceUsingCRUDRepository;
import com.example.demo.Service.Interface.ItemService;
import com.example.demo.Service.Interface.PartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequiredArgsConstructor
public class PartController {

    private final PartService partService;
    private final Order order;
    private final ItemService itemService;

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


        if (itemService.itemExistsInOrder((long) partId, order)) {
            return "error/error_item_in_order";
        }
        if(partService.partInUse((long) partId)){
            return "error/error_part_in_use";
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
    @PostMapping("/processFormAddInPart")
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
    @PostMapping("/processFormAddOutPart")
    public String submitForm(@Valid @ModelAttribute("outsourcedpart") OutsourcedPart part, BindingResult bindingResult, Model model){

        model.addAttribute("outsourcedpart",part);

        if(bindingResult.hasErrors()){
            return "form/outsourced_part_form";
        }

        System.out.println(part);
        partService.save(part);
        return "confirmation/confirmationaddpart";
    }

}
