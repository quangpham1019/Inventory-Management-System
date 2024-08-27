package com.example.demo.Controller;

import com.example.demo.Domain.InhousePart;
import com.example.demo.Domain.OutsourcedPart;
import com.example.demo.Domain.Part;
import com.example.demo.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
public class PartController {

    private PartService partService;

    @Autowired
    public PartController(PartService partService) {
        this.partService = partService;
    }

    @GetMapping("/showPartFormForUpdate")
    public String showPartFormForUpdate(@RequestParam("partID") int partId, Model model){

        var curPart = partService.findById(partId);
        String partType, form;

        if (curPart.getClass().equals(InhousePart.class)) {
            partType = "inhousepart";
            form = "inhouse_part_form";
        } else {
            partType = "outsourcedpart";
            form = "outsourced_part_form";
        }

        model.addAttribute(partType, curPart);
        return form;
    }

    @GetMapping("/deletepart")
    public String deletePart(@Valid @RequestParam("partID") int partId){

        Part part = partService.findById(partId);

        if(part.getProducts().isEmpty()){
            partService.deleteById(partId);
            return "confirmation/confirmationdeletepart";
        }

        return "error/error_not_enuf_parts";
    }

    // inhouse
    @GetMapping("/showFormAddInPart")
    public String showFormAddInhousePart(Model model){

        model.addAttribute("inhousepart", new InhousePart());

        return "inhouse_part_form";
    }

    @PostMapping("/showFormAddInPart")
    public String submitForm(@Valid @ModelAttribute("inhousepart") InhousePart part, BindingResult theBindingResult, Model model){

        model.addAttribute("inhousepart",part);

        if(theBindingResult.hasErrors()){
            return "inhouse_part_form";
        }

        partService.save(part);
        return "confirmation/confirmationaddpart";
    }

    // outsourced
    @GetMapping("/showFormAddOutPart")
    public String showFormAddOutsourcedPart(Model model){

        model.addAttribute("outsourcedpart", new OutsourcedPart());

        return "outsourced_part_form";
    }

    @PostMapping("/showFormAddOutPart")
    public String submitForm(@Valid @ModelAttribute("outsourcedpart") OutsourcedPart part, BindingResult bindingResult, Model model){

        model.addAttribute("outsourcedpart",part);

        if(bindingResult.hasErrors()){
            return "outsourced_part_form";
        }

        partService.save(part);
        return "confirmation/confirmationaddpart";
    }

}
