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