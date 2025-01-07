package com.example.demo.Controller;
import com.example.demo.Security.AppUser;
import com.example.demo.Service.Data.Interface.JcsServicingService;
import com.example.demo.Service.Data.Interface.PartService;
import com.example.demo.Service.Data.Interface.ProductService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Controller
public class InventoryController {

    private PartService partService;
    private ProductService productService;
    private JcsServicingService jcsServicingService;

    @ModelAttribute
    AppUser appUser(@AuthenticationPrincipal AppUser appUser) {
        return appUser;
    }

    public InventoryController(PartService partService, ProductService productService, JcsServicingService jcsServicingService){
        this.partService=partService;
        this.productService=productService;
        this.jcsServicingService = jcsServicingService;
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
