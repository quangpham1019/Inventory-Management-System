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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
            Model theModel,
            @Param("partkeyword") String partkeyword,
            @Param("productkeyword") String productkeyword,
            @Param("serviceKeyword") String serviceKeyword){

        List<Part> partList = partService.listAllByKeyword(partkeyword);
        theModel.addAttribute("parts",partList);
        theModel.addAttribute("partkeyword",partkeyword);

    //    theModel.addAttribute("products",productService.findAll());
        List<Product> productList = productService.listAll(productkeyword);
        theModel.addAttribute("products", productList);
        theModel.addAttribute("productkeyword",productkeyword);

        List<Service> serviceList = jcsServiceService.listAllByKeyword(serviceKeyword);
        theModel.addAttribute("serviceList", serviceList);
        theModel.addAttribute("serviceKeyword", serviceKeyword);

        theModel.addAttribute("disabled", true);
        System.out.println(serviceList);
        return "menu pages/inventory";
    }

    @GetMapping("/addService")
    public String getAddServiceForm(Model model) {
        model.addAttribute("service", new Service());
        model.addAttribute("action", "add");
        return "service_form";
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
        return "service_form";
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
