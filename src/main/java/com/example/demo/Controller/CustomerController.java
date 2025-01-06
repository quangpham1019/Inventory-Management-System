package com.example.demo.Controller;

import com.example.demo.Domain.Customer;
import com.example.demo.Service.Interface.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/addCustomer")
    public String getNewCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("action", "add");
        return "form/customer_form";
    }

    @PostMapping("/processCustomer")
    public String processCustomer(@ModelAttribute Customer customer) {
        customerService.save(customer);
        return "redirect:/api/v1/sales";
    }
    //
    @GetMapping("/manageCustomers")
    public String getManageCustomersPage(Model model) {
        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);
        return "menu pages/customers";
    }

    @GetMapping("/updateCustomer")
    public String getUpdateCustomerPage(@RequestParam int customerId,
                                        Model model) {

        Customer updateCustomer = customerService.findById((long) customerId);
        model.addAttribute("customer", updateCustomer);
        model.addAttribute("action", "update");
        return "form/customer_form";
    }
    @PostMapping("/updateCustomer")
    public String updateCustomerProcess(@RequestParam int customerId,
                                        @ModelAttribute("customer") Customer updateCustomer) {

        customerService.deleteById((long) customerId);
        customerService.save(updateCustomer);
        return "redirect:/api/v1/customer/manageCustomers";
    }

    @GetMapping("/deleteCustomer")
    public String deleteCustomer(@RequestParam int customerId) {
        customerService.deleteById((long) customerId);
        return "redirect:/api/v1/customer/manageCustomers";
    }
}
