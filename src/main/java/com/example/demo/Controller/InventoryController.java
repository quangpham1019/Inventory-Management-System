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

    // TODO: update search result without reloading whole page
    // guide @ https://stackoverflow.com/questions/70202040/update-content-in-thymeleaf-without-reloading-whole-page
    @PostMapping("/clearKeyword")
    public String clearKeyword(Model model){
        return "fragments/tableFragments :: anotherTable";
    }


    @GetMapping("")
    public String redirect() {
        return "redirect:/inventory";
    }
    @GetMapping("/inventory")
    public String listPartsAndProducts(
            Model model,
            @Param("partkeyword") String partkeyword,
            @Param("productkeyword") String productkeyword,
            @Param("serviceKeyword") String serviceKeyword){

        List<Part> partList = partService.listAllByKeyword(partkeyword);
        model.addAttribute("parts",partList);
        model.addAttribute("partkeyword",partkeyword);

        List<Product> productList = productService.listAll(productkeyword);
        model.addAttribute("products", productList);
        model.addAttribute("productkeyword",productkeyword);

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
}
