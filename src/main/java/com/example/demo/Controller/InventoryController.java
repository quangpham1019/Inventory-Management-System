package com.example.demo.Controller;
import com.example.demo.Domain.JcsServicing;
import com.example.demo.Domain.Order;
import com.example.demo.Security.AppUser;
import com.example.demo.Service.Interface.JcsServicingService;
import com.example.demo.Service.Interface.PartService;
import com.example.demo.Service.Interface.ProductService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Controller
public class InventoryController {

    private Order order;
    private PartService partService;
    private ProductService productService;
    private JcsServicingService jcsServicingService;

    @ModelAttribute
    AppUser appUser(@AuthenticationPrincipal AppUser appUser) {
        return appUser;
    }

    public InventoryController(PartService partService, ProductService productService, JcsServicingService jcsServicingService, Order order){
        this.partService=partService;
        this.productService=productService;
        this.jcsServicingService = jcsServicingService;
        this.order = order;
    }

    @GetMapping("")
    public String redirect() {
        return "redirect:/inventory";
    }
    @GetMapping("/inventory")
    public String listPartsAndProducts(
            Model model) {

        model.addAttribute("parts", partService.listAllByKeyword(""));
        model.addAttribute("products", productService.listAllByKeyword(""));
        model.addAttribute("jcsServicingList", jcsServicingService.listAllByKeyword(""));
        model.addAttribute("disabled", true);

        return "menu pages/inventory";
    }

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
    public String updateJcsServicingProcess(@RequestParam int serviceId,
                                    @ModelAttribute("jcsServicing") JcsServicing updateJcsServicing) {

        JcsServicing jcsServicing = jcsServicingService.findById(serviceId);
        jcsServicing.setDuration(updateJcsServicing.getDuration());
        jcsServicing.setName(updateJcsServicing.getName());
        jcsServicing.setPrice(updateJcsServicing.getPrice());
        jcsServicingService.save(jcsServicing);
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

    @PostMapping("/findByKeyword/{table}/{keyword}")
    public String findByKeyword(Model model,
                                @PathVariable String table,
                                @PathVariable String keyword) {

        String fragment = "";
        keyword = keyword.equals("CLEAR_KEYWORD") ? "" : keyword;

        fragment = switch (table) {
            case "part" -> {
                model.addAttribute("parts", partService.listAllByKeyword(keyword));
                yield "fragments/inventoryTable :: partTable";
            }
            case "product" -> {
                model.addAttribute("products", productService.listAllByKeyword(keyword));
                yield "fragments/inventoryTable :: productTable";
            }
            case "jcsServicing" -> {
                model.addAttribute("jcsServicingList", jcsServicingService.listAllByKeyword(keyword));
                yield "fragments/inventoryTable :: jcsServicingTable";
            }
            default -> fragment;
        };
        return fragment;
    }
}
