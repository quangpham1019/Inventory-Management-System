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