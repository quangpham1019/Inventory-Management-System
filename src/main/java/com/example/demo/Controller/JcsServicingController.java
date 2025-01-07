package com.example.demo.Controller;

import com.example.demo.Domain.JcsServicing;
import com.example.demo.Domain.Order;
import com.example.demo.Service.Interface.JcsServicingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/jcsServicing")
@RequiredArgsConstructor
public class JcsServicingController {

    private final JcsServicingService jcsServicingService;
    private final Order order;

    @GetMapping("/addJcsServicing")
    public String getAddServiceForm(Model model) {
        model.addAttribute("jcsServicing", new JcsServicing());
        model.addAttribute("action", "add");
        return "form/service_form";
    }

    @PostMapping("/processJcsServicing")
    public String processNewService(@ModelAttribute(name = "jcsServicing") JcsServicing newJcsServicing) {
        jcsServicingService.save(newJcsServicing);
        return "redirect:/";
    }

    @GetMapping("/updateJcsServicing")
    public String getUpdateService(@RequestParam int serviceId, Model model) {

        JcsServicing updateJcsServicing = jcsServicingService.findById(serviceId);

        model.addAttribute("jcsServicing", updateJcsServicing);
        model.addAttribute("action", "update");

        return "form/service_form";
    }
    @PostMapping("/updateJcsServicing")
    public String updateJcsServicingProcess(@ModelAttribute("jcsServicing") JcsServicing updateJcsServicing) {
        jcsServicingService.save(updateJcsServicing);
        return "redirect:/";
    }
    @GetMapping("/deleteJcsServicing")
    public String deleteJcsServicing(@RequestParam int serviceId) {
        JcsServicing jcsServicing = jcsServicingService.findById(serviceId);
        if (order.getOrderItemSet()
                .stream()
                .anyMatch(orderItem -> orderItem.getItem().equals(jcsServicing))) {
            return "error/error_item_in_order";
        }
        jcsServicingService.deleteById(serviceId);
        return "redirect:/";
    }
}
