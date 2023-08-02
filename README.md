**C.  Customize the HTML user interface for your customer’s application. The user interface should include the shop name, the product names, and the names of the parts.**

demo.css

```
// line 2-8

body {
background-color: antiquewhite;
}

h1 {
text-align: center;
}
```
mainscreen.html
```
// line 13

<link rel="stylesheet" href="css/demo.css">

// line 19

<h1>Jack's Computer Shop</h1>
```
InhousePartForm.html, OutsourcedPartForm.html
```
// line 8-10

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
      integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<link rel="stylesheet" href="css/demo.css">
    
// line 13, 36

<div class="container">
...
</div>    
```
productForm.html
```
// line 8-10

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
      integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<link rel="stylesheet" href="css/demo.css">

// line 14, 79

<div class="container">
...
</div>
```
**D.  Add an “About” page to the application to describe your chosen customer’s company to web viewers and include navigation to and from the “About” page and the main screen.**

resources/templates/mainscreen.html
```
// line 14

<title>Jack's Computer Shop</title>

// line 20

<a href="http://localhost:8080/about.html">About Us</a>
```
resources/static/about.html
```
An html file with the following:
- a heading "About Jack's Computer Shop", 
- a paragraph describing the shop history and store branches, 
- a contact us section with contact email, phone, and store addresses
- a link to the main screen 
```
**E.Add a sample inventory appropriate for your chosen store to the application. You should have five parts and five products in your sample inventory and should not overwrite existing data in the database.**

BootStrapData.java
```
// line 33, 36, 40
Injecting InhousePartRepository to save inhouse parts data

// line 73-96
Check to see if the part and product repositories are empty
If so, initialize a sample inventory of 5 new parts and 5 new products
Save the new parts and products to the repositories

if(productRepository.count() == 0 && partRepository.count() == 0) {

      InhousePart laptop_screen = new InhousePart("Laptop Screen", 140.00, 30);
      InhousePart gaming_case = new InhousePart("Gaming Case", 100.00, 25);
      InhousePart  pc_fan = new InhousePart("80mm Silent PC Fan", 15.00, 20);
      OutsourcedPart samsing_ram = new OutsourcedPart("Samsing", "Samsing 16GB DDR4 3200MHz Laptop RAM", 60.00, 30);
      OutsourcedPart outtel_cpu = new OutsourcedPart("Outtel", "Outtel Core i11 13th Gen CPU", 700.00, 15);
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
```
InhousePart.java
```
// line 20-22
create an InhousePart constructor that invokes parent Part constructor
--> simplify the initialization of InhousePart in BootStrapData.java

public InhousePart(String name, double price, int inv) {
    super(name, price, inv);
}
```
OutsourcedPart.java
```
// line 20-23
create an OutsourcedPart constructor that invokes parent Part constructor
--> simplify the initialization of OutsourcedPart in BootStrapData.java

public OutsourcedPart(String companyName, String name, double price, int inv) {
    super(name, price, inv);
    this.companyName = companyName;
}
```
**F.  Add a “Buy Now” button to your product list. Your “Buy Now” button must meet each of the following parameters:**

**•   The “Buy Now” button must be next to the buttons that update and delete products.**

mainscreen.html
```
// line 84
<a th:href="@{/showProductFormForBuyNow(productID=${tempProduct.id})}" 
    class="btn btn-primary btn-sm mb-3">Buy Now</a>
```
**•   The button should decrement the inventory of that product by one. It should not affect the inventory of any of the associated parts.**

AddProductController.java
```
// line 110-122

@GetMapping("/showProductFormForBuyNow")
public String showProductFormForBuyNow(@RequestParam("productID") int theId, Model theModel){
    ProductService productService = context.getBean(ProductServiceImpl.class);
    Product theProduct = productService.findById(theId);
    if (theProduct.getInv() > 0) {
        theProduct.setInv(theProduct.getInv() - 1);
        productService.save(theProduct);
        theModel.addAttribute("success", true);
    } else {
        theModel.addAttribute("success", false);
    }
    return "confirmationbuynowproduct";
}
```
**•   Display a message that indicates the success or failure of a purchase.**

confirmationbuynowproduct.html
```
An html page that check to see if the Buy Now process is successful, then
Display the corresponding messages:
- If success, 'You have successfully bought this product!'
- If not success, 'This product is currently out of stock!'
```
**G. Modify the parts to track maximum and minimum inventory by doing the following:**

**•   Add additional fields to the part entity for maximum and minimum inventory.**

Part.java
```
// line 34-35, 
int minInv;
int maxInv;

// line 45-50
public Part(String name, double price, int inv, int minInv, int maxInv) {
    this.name = name;
    this.price = price;
    this.inv = inv;
    this.minInv = minInv;
    this.maxInv = maxInv;
}

// line 92-107
adding getters and setters for minInv and maxInv 
```
InhousePart.java, OutsourcedPart.java
```
// line 20-21
modify the InhousePart and OutsourcedPart constructors to include minInv and maxInv
```
mainscreen.html
```
// line 39-40
<th>Min Inventory</th>
<th>Max Inventory</th>

// line 49-50
<td th:text="${tempPart.minInv}">1</td>
<td th:text="${tempPart.maxInv}">1</td>
```
**•   Modify the sample inventory to include the maximum and minimum fields.**
BootStrapData.java
```
// line 75-79
InhousePart laptop_screen = new InhousePart("Laptop Screen", 140.00, 30, 5, 50);
InhousePart gaming_case = new InhousePart("Gaming Case", 100.00, 25, 5, 40);
InhousePart  pc_fan = new InhousePart("80mm Silent PC Fan", 15.00, 20, 4, 40);
OutsourcedPart samsing_ram = new OutsourcedPart("Samsing", "Samsing 16GB DDR4 3200MHz Laptop RAM", 60.00, 30, 5, 50);
OutsourcedPart outtel_cpu = new OutsourcedPart("Outtel", "Outtel Core i11 13th Gen CPU", 700.00, 15, 2, 25);
```
**•   Add to the InhousePartForm and OutsourcedPartForm forms additional text inputs for the inventory so the user can set the maximum and minimum values.**
InhousePartForm.html
```
// line 28-29
<p><input type="text" path="minInv" th:field="*{minInv}" placeholder="Inventory" class="form-control mb-4 col-4"/></p>
<p><input type="text" path="maxInv" th:field="*{maxInv}" placeholder="Inventory" class="form-control mb-4 col-4"/></p>
```
OutsourcedPartForm.html
```
// line 29-30
<p><input type="text" th:field="*{minInv}" placeholder="Inventory" class="form-control mb-4 col-4"/></p>
<p><input type="text" th:field="*{maxInv}" placeholder="Inventory" class="form-control mb-4 col-4"/></p>
```
**•   Rename the file the persistent storage is saved to.**
application.properties
```
// line 7-8
spring.datasource.generate-unique-name=false
spring.datasource.name=jackcomputershop
```
**•   Modify the code to enforce that the inventory is between or at the minimum and maximum value.**

ValidPartInventory.java
```
package com.example.demo.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {PartInventoryValidator.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPartInventory {
    String message() default "Inventory value must be between min inventory and max inventory.";
    Class<?> [] groups() default {};
    Class<? extends Payload> [] payload() default {};
}
```
PartInventoryValidator.java
```
package com.example.demo.validators;

import com.example.demo.domain.Part;
import com.example.demo.service.PartService;
import com.example.demo.service.PartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PartInventoryValidator implements ConstraintValidator<ValidPartInventory, Part> {
    @Autowired
    private ApplicationContext context;

    public static  ApplicationContext myContext;

    @Override
    public void initialize(ValidPartInventory constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Part part, ConstraintValidatorContext constraintValidatorContext) {
        if (context==null) return true;
        if (context!=null) myContext = context;
        if (part.getInv() >= part.getMinInv() && part.getInv() <= part.getMaxInv()) {
            return true;
        }
        else {
            return false;
        }
    }
}

```
Part.java
```
// line 20
@ValidPartInventory

--> apply the validation to class Part
```