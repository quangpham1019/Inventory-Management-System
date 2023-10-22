package com.example.demo.bootstrap;

import com.example.demo.domain.*;
import com.example.demo.repositories.*;
import com.example.demo.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 *
 *
 *
 *
 */
@Component
public class BootStrapData implements CommandLineRunner {

    private final JcsUserService jcsUserService;
    private final CustomerService customerService;
    private final JcsServiceService jcsServiceService;
    private final ProductService productService;
    private final PartService partService;

    public BootStrapData(JcsUserService jcsUserService, CustomerService customerService, JcsServiceService jcsServiceService, ProductService productService, PartService partService) {
        this.jcsUserService = jcsUserService;
        this.customerService = customerService;
        this.jcsServiceService = jcsServiceService;
        this.productService = productService;
        this.partService = partService;
    }

    @Override
    public void run(String... args) throws Exception {

        if(productService.findAll().isEmpty() && partService.findAll().isEmpty()) {

            Product samsing_laptop = new Product("Samsing Laptop", 800.00, 30);
            Product outtel_laptop = new Product("Outtel Laptop", 950.00, 40);
            Product buck_gaming_pc = new Product("Buck Gaming PC", 1500.00, 20);
            Product bucko_workstation = new Product("Bucko Workstation", 3000.00, 10);
            Product macrohard_diveback = new Product("Macrohard Diveback 2-in-1 Laptop", 600.00, 35);
            productService.saveAll(new ArrayList<>(Arrays.asList(samsing_laptop, outtel_laptop, bucko_workstation, buck_gaming_pc, macrohard_diveback)));

            InhousePart laptop_screen = new InhousePart("Bucko Laptop Screen", 140.00, 30, 5, 50);
            InhousePart mini_laptop_screen = new InhousePart("Bucko Mini Laptop Screen", 200.00, 35, 5, 50);
            InhousePart gaming_case = new InhousePart("Bucko PC Gaming Case", 100.00, 25, 5, 40);
            InhousePart pc_fan = new InhousePart("80mm Silent PC Fan", 50.00, 20, 4, 40);
            InhousePart bucko_workstation_case = new InhousePart("Bucko Workstation Case", 300.00, 10, 2, 25);
            InhousePart bucko_motherboard = new InhousePart("Bucko Ultra-protective Motherboard", 800.00, 15, 5, 30);
            InhousePart bucko_laptop_body = new InhousePart("Bucko Ultra-thin Shock-dispersing Laptop Body", 50.00, 30, 10, 60);
            OutsourcedPart macro_keyboard = new OutsourcedPart("Macro Diveback", "Macro Diveback Keyboard", 300.00, 15, 5, 30);
            OutsourcedPart samsing_motherboard = new OutsourcedPart("Samsing", "Samsing prebuilt Motherboard w/ RAM & CPU", 600.00, 30, 5, 50);
            OutsourcedPart outtel_motherboard = new OutsourcedPart("Outtel", "Outtel prebuilt Motherboard w/ RAM & CPU", 700.00, 15, 2, 25);

            setPartToProduct(samsing_laptop, laptop_screen, samsing_motherboard, bucko_laptop_body);
            setPartToProduct(outtel_laptop, laptop_screen, outtel_motherboard, bucko_laptop_body);
            setPartToProduct(buck_gaming_pc, gaming_case, pc_fan, bucko_motherboard);
            setPartToProduct(bucko_workstation, bucko_workstation_case, pc_fan, bucko_motherboard);
            setPartToProduct(macrohard_diveback, macro_keyboard, mini_laptop_screen);

            partService.saveAll(new ArrayList<>(Arrays.asList(
                    laptop_screen, mini_laptop_screen, gaming_case, pc_fan, bucko_workstation_case, bucko_motherboard, bucko_laptop_body,
                    macro_keyboard, samsing_motherboard, outtel_motherboard
            )));
        }

        if (!jcsUserService.hasAdminRole()) {
            User adminUser = new User("jcsadmin@gmail.com",
                    new BCryptPasswordEncoder().encode("jcsadmin"),
                    "adminFirst",
                    "adminLast",
                    RoleType.ADMIN);
            jcsUserService.save(adminUser);
        }

        if (!jcsUserService.hasUserRole()) {
            User user = new User("jcsuser@gmail.com",
                    new BCryptPasswordEncoder().encode("jcsuser"),
                    "userFirst",
                    "userLast",
                    RoleType.USER);
            jcsUserService.save(user);
        }

        if (!customerService.hasCustomer()) {
            Customer customer1 = new Customer("jackpham@gmail.com", "Jack", "Pham", "1321 Strong Oak Rd", "Columbus", "OH", "43229");
            Customer customer2 = new Customer("johndoe@gmail.com", "John", "Doe", "4432 Hayden St", "Dublin", "OH", "43235");
            Customer customer3 = new Customer("jimjerry@gmail.com", "Jim", "Jerry", "1232 Toylane Rd", "Columbus", "OH", "43230");
            Customer customer4 = new Customer("annadoschun@gmail.com", "Anna", "Doschun", "5532 Strangely St", "New Albany", "OH", "43232");
            Customer customer5 = new Customer("navenumbriga@gmail.com", "Naven", "Umbriga", "2635 Ready Rd", "Dayton", "OH", "43228");
            Customer customer6 = new Customer("lynnshim@gmail.com", "Lynn", "Shim", "1127 Brewing Br", "Delaware", "OH", "43015");
            customerService.saveAll(new ArrayList<>(Arrays.asList(customer1, customer2, customer3, customer4, customer5, customer6)));
        }

        if (!jcsServiceService.hasService()) {
            Service service1 = new Service("Replace battery", 120.0, 2);
            Service service2 = new Service("Upgrade RAM", 100.0, 2);
            Service service3 = new Service("Upgrade hard drive", 80.0, 2);
            Service service4 = new Service("Replace laptop screen", 150.0, 4);
            jcsServiceService.saveAll(new ArrayList<>(Arrays.asList(service1, service2, service3, service4)));
        }
    }
    public void setPartToProduct(Product product, Part... parts) {
        for (Part p: parts) {
            p.getProducts().add(product);
        }
    }
}
