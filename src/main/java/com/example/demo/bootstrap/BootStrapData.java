package com.example.demo.bootstrap;

import com.example.demo.domain.InhousePart;
import com.example.demo.domain.OutsourcedPart;
import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.repositories.InhousePartRepository;
import com.example.demo.repositories.OutsourcedPartRepository;
import com.example.demo.repositories.PartRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.service.OutsourcedPartService;
import com.example.demo.service.OutsourcedPartServiceImpl;
import com.example.demo.service.ProductService;
import com.example.demo.service.ProductServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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

    private final InhousePartRepository inhousePartRepository;
    private final OutsourcedPartRepository outsourcedPartRepository;

    public BootStrapData(PartRepository partRepository, ProductRepository productRepository, OutsourcedPartRepository outsourcedPartRepository, InhousePartRepository inhousePartRepository) {
        this.partRepository = partRepository;
        this.productRepository = productRepository;
        this.outsourcedPartRepository=outsourcedPartRepository;
        this.inhousePartRepository = inhousePartRepository;
    }

    @Override
    public void run(String... args) throws Exception {

       /*
        OutsourcedPart o= new OutsourcedPart();
        o.setCompanyName("Western Governors University");
        o.setName("out test");
        o.setInv(5);
        o.setPrice(20.0);
        o.setId(100L);
        outsourcedPartRepository.save(o);
        OutsourcedPart thePart=null;
        List<OutsourcedPart> outsourcedParts=(List<OutsourcedPart>) outsourcedPartRepository.findAll();
        for(OutsourcedPart part:outsourcedParts){
            if(part.getName().equals("out test"))thePart=part;
        }

        System.out.println(thePart.getCompanyName());
        */
        List<OutsourcedPart> outsourcedParts=(List<OutsourcedPart>) outsourcedPartRepository.findAll();
        for(OutsourcedPart part:outsourcedParts){
            System.out.println(part.getName()+" "+part.getCompanyName());
        }

        /*
        Product bicycle= new Product("bicycle",100.0,15);
        Product unicycle= new Product("unicycle",100.0,15);
        productRepository.save(bicycle);
        productRepository.save(unicycle);
        */
        if(productRepository.count() == 0 && partRepository.count() == 0) {

            InhousePart laptop_screen = new InhousePart("Laptop Screen", 140.00, 30, 5, 50);
            InhousePart gaming_case = new InhousePart("Gaming Case", 100.00, 25, 5, 40);
            InhousePart  pc_fan = new InhousePart("80mm Silent PC Fan", 15.00, 20, 4, 40);
            OutsourcedPart samsing_ram = new OutsourcedPart("Samsing", "Samsing 16GB DDR4 3200MHz Laptop RAM", 60.00, 30, 5, 50);
            OutsourcedPart outtel_cpu = new OutsourcedPart("Outtel", "Outtel Core i11 13th Gen CPU", 700.00, 15, 2, 25);
            inhousePartRepository.save(laptop_screen);
            inhousePartRepository.save(gaming_case);
            inhousePartRepository.save(pc_fan);
            outsourcedPartRepository.save(samsing_ram);
            outsourcedPartRepository.save(outtel_cpu);

            Product lenivi_laptop = new Product("Lenivi Laptop", 800.00, 30);
            Product dall_laptop = new Product("Dall Laptop", 900.00, 40);
            Product buck_gaming_pc = new Product("Buck Gaming PC", 1800.00, 20);
            Product bucko_workstation = new Product("Bucko Workstation", 3000.00, 10);
            Product macrohard_diveback = new Product("Macrohard Diveback 2-in-1 Laptop", 600.00, 35);
            productRepository.save(lenivi_laptop);
            productRepository.save(dall_laptop);
            productRepository.save(buck_gaming_pc);
            productRepository.save(bucko_workstation);
            productRepository.save(macrohard_diveback);
        }

        System.out.println("Started in Bootstrap");
        System.out.println("Number of Products"+productRepository.count());
        System.out.println(productRepository.findAll());
        System.out.println("Number of Parts"+partRepository.count());
        System.out.println(partRepository.findAll());

    }
}
