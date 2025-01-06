package com.example.demo.Controller;
import com.example.demo.Domain.Order;
import com.example.demo.Security.AppUser;
import com.example.demo.Domain.Service;
import com.example.demo.Service.JcsServiceService.JcsServicingService;
import com.example.demo.Service.PartService.PartService;
import com.example.demo.Service.ProductService.ProductService;
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
        model.addAttribute("serviceList", jcsServicingService.listAllByKeyword(""));
        model.addAttribute("disabled", true);

        return "menu pages/inventory";
    }

    @GetMapping("/addService")
    public String getAddServiceForm(Model model) {
        model.addAttribute("service", new Service());
        model.addAttribute("action", "add");
        return "form/service_form";
    }

    @PostMapping("/processService")
    public String processNewService(@ModelAttribute(name = "service") Service newService) {
        jcsServicingService.save(newService);
        return "redirect:/";
    }

    @GetMapping("/updateService")
    public String getUpdateService(@RequestParam int serviceId, Model model) {

        Service updateService = jcsServicingService.findById(serviceId);
        model.addAttribute("service", updateService);
        model.addAttribute("action", "update");
        return "form/service_form";
    }
    @PostMapping("/updateService")
    public String updateServiceProcess(@RequestParam int serviceId,
                                    @ModelAttribute("service") Service updateService) {

        Service service = jcsServicingService.findById(serviceId);
        service.setDuration(updateService.getDuration());
        service.setName(updateService.getName());
        service.setPrice(updateService.getPrice());
        jcsServicingService.save(service);
        return "redirect:/";
    }

    @GetMapping("/deleteService")
    public String deleteService(@RequestParam int serviceId) {
        Service service = jcsServicingService.findById(serviceId);
        if (order.getOrderItemSet()
                .stream()
                .anyMatch(orderItem -> orderItem.getItem().equals(service))) {
            return "error/error_item_in_order";
        }
        jcsServicingService.deleteById(serviceId);
        return "redirect:/";
    }

    @PostMapping("/findByKeyword/{table}/{keyword}")
    public String clearPartKeyword(Model model,
                                   @PathVariable String table,
                                   @PathVariable String keyword) {

        String fragment = "";
        keyword = keyword.equals("CLEAR_KEYWORD") ? "" : keyword;

        switch (table) {
            case "part":
                model.addAttribute("parts", partService.listAllByKeyword(keyword));
                fragment = "fragments/inventoryTable :: partTable";
                break;
            case "product":
                model.addAttribute("products", productService.listAllByKeyword(keyword));
                fragment = "fragments/inventoryTable :: productTable";
                break;
            case "service":
                model.addAttribute("serviceList", jcsServicingService.listAllByKeyword(keyword));
                fragment = "fragments/inventoryTable :: serviceTable";
                break;
        }
        return fragment;
    }
}
