package com.example.demo.Controller;
import com.example.demo.Security.AppUser;
import com.example.demo.Domain.Part;
import com.example.demo.Domain.Product;
import com.example.demo.Domain.Service;
import com.example.demo.Service.JcsServiceService;
import com.example.demo.Service.PartService;
import com.example.demo.Service.ProductService;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@CrossOrigin
@Controller
public class InventoryController {

    private PartService partService;
    private ProductService productService;
    private JcsServiceService jcsServiceService;

    @ModelAttribute
    AppUser appUser(@AuthenticationPrincipal AppUser appUser) {
        return appUser;
    }

    public InventoryController(PartService partService, ProductService productService, JcsServiceService jcsServiceService){
        this.partService=partService;
        this.productService=productService;
        this.jcsServiceService = jcsServiceService;
    }

    @GetMapping("")
    public String redirect() {
        return "redirect:/inventory";
    }
    @GetMapping("/inventory")
    public String listPartsAndProducts(
            Model model,
            @Param("partKeyword") String partKeyword,
            @Param("productKeyword") String productKeyword,
            @Param("serviceKeyword") String serviceKeyword){

        List<Part> partList = partService.listAllByKeyword(partKeyword);
        model.addAttribute("parts",partList);
        model.addAttribute("partKeyword",partKeyword);

        List<Product> productList = productService.listAll(productKeyword);
        model.addAttribute("products", productList);
        model.addAttribute("productKeyword",productKeyword);

        List<Service> serviceList = jcsServiceService.listAllByKeyword(serviceKeyword);
        model.addAttribute("serviceList", serviceList);
        model.addAttribute("serviceKeyword", serviceKeyword);

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
        jcsServiceService.save(newService);
        return "redirect:/";
    }

    @GetMapping("/updateService")
    public String getUpdateService(@RequestParam int serviceId, Model model) {

        Service updateService = jcsServiceService.findById(serviceId);
        model.addAttribute("service", updateService);
        model.addAttribute("action", "update");
        return "form/service_form";
    }
    @PostMapping("/updateService")
    public String updateServiceProcess(@RequestParam int serviceId,
                                    @ModelAttribute("service") Service updateService) {

        Service service = jcsServiceService.findById(serviceId);
        service.setDuration(updateService.getDuration());
        service.setName(updateService.getName());
        service.setPrice(updateService.getPrice());
        jcsServiceService.save(service);
        return "redirect:/";
    }

    @GetMapping("/deleteService")
    public String deleteService(@RequestParam int serviceId) {
        jcsServiceService.deleteById(serviceId);
        return "redirect:/";
    }

    @PostMapping("/clearKeyword/{table}")
    public String clearPartKeyword(Model model,
                                   @PathVariable String table) {

        String fragment = "";
        switch (table) {
            case "part":
                model.addAttribute("parts", partService.findAll());
                fragment = "fragments/inventoryTable :: partTable";
                break;
            case "product":
                model.addAttribute("products", productService.listAll(""));
                fragment = "fragments/inventoryTable :: productTable";
                break;
            case "service":
                model.addAttribute("serviceList", jcsServiceService.listAllByKeyword(""));
                fragment = "fragments/inventoryTable :: serviceTable";
                break;
        }
        return fragment;
    }
}
