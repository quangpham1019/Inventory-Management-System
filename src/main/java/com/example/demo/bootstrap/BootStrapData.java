package com.example.demo.bootstrap;

import com.example.demo.domain.*;
import com.example.demo.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 *
 *
 *
 */
@Component
public class BootStrapData implements CommandLineRunner {

    private final PartRepository partRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final InhousePartRepository inhousePartRepository;
    private final OutsourcedPartRepository outsourcedPartRepository;

    public BootStrapData(UserRepository userRepository, PartRepository partRepository, ProductRepository productRepository, OutsourcedPartRepository outsourcedPartRepository, InhousePartRepository inhousePartRepository) {
        this.partRepository = partRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.outsourcedPartRepository=outsourcedPartRepository;
        this.inhousePartRepository = inhousePartRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        List<OutsourcedPart> outsourcedParts=(List<OutsourcedPart>) outsourcedPartRepository.findAll();
        for(OutsourcedPart part:outsourcedParts){
            System.out.println(part.getName()+" "+part.getCompanyName());
        }

        if(productRepository.count() == 0 && partRepository.count() == 0) {

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

            Product samsing_laptop = new Product("Samsing Laptop", 800.00, 30);
            Product outtel_laptop = new Product("Outtel Laptop", 950.00, 40);
            Product buck_gaming_pc = new Product("Buck Gaming PC", 1500.00, 20);
            Product bucko_workstation = new Product("Bucko Workstation", 3000.00, 10);
            Product macrohard_diveback = new Product("Macrohard Diveback 2-in-1 Laptop", 600.00, 35);

            productRepository.save(macrohard_diveback);
            productRepository.save(samsing_laptop);
            productRepository.save(outtel_laptop);
            productRepository.save(buck_gaming_pc);
            productRepository.save(bucko_workstation);

            setPartToProduct(samsing_laptop, laptop_screen, samsing_motherboard, bucko_laptop_body);
            setPartToProduct(outtel_laptop, laptop_screen, outtel_motherboard, bucko_laptop_body);
            setPartToProduct(buck_gaming_pc, gaming_case, pc_fan, bucko_motherboard);
            setPartToProduct(bucko_workstation, bucko_workstation_case, pc_fan, bucko_motherboard);
            setPartToProduct(macrohard_diveback, macro_keyboard, mini_laptop_screen);

            partRepository.save(laptop_screen);
            partRepository.save(mini_laptop_screen);
            partRepository.save(bucko_laptop_body);
            partRepository.save(macro_keyboard);
            partRepository.save(bucko_workstation_case);
            partRepository.save(gaming_case);
            partRepository.save(pc_fan);
            partRepository.save(samsing_motherboard);
            partRepository.save(outtel_motherboard);
            partRepository.save(bucko_motherboard);
        }

//        System.out.println("Started in Bootstrap");
//        System.out.println("Number of Products"+productRepository.count());
//        System.out.println(productRepository.findAll());
//        System.out.println("Number of Parts"+partRepository.count());
//        System.out.println(partRepository.findAll());

        User adminAccount = userRepository.findByRole(Role.ADMIN);
        if (adminAccount == null) {
            User user = new User();

            user.setEmail("admin1@gmail.com");
            user.setFirstName("admin1");
            user.setLastName("admin1");
            user.setRole(Role.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin1"));

            userRepository.save(user);
        }
    }
    public void setPartToProduct(Product product, Part... parts) {
        for (Part p: parts) {
            p.getProducts().add(product);
        }
    }
}
