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
