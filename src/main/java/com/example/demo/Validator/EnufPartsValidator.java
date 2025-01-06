package com.example.demo.Validator;

import com.example.demo.Domain.Part;
import com.example.demo.Domain.Product;
import com.example.demo.Service.ProductService.ProductService;
import com.example.demo.Service.ProductService.ProductServiceWithCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 *
 *
 *
 *
 */
public class EnufPartsValidator implements ConstraintValidator<ValidEnufParts, Product> {
    @Autowired
    private ApplicationContext context;
    public static  ApplicationContext myContext;
    @Override
    public void initialize(ValidEnufParts constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Product product, ConstraintValidatorContext constraintValidatorContext) {
        if(context==null) return true;
        if(context!=null)myContext=context;
        ProductService repo = myContext.getBean(ProductServiceWithCRUD.class);
        if (product.getId() != 0) {
            Product myProduct = repo.findById((long) product.getId());
            for (Part p : myProduct.getParts()) {
                if ((p.getInv()-p.getMinInv())<(product.getInv()-myProduct.getInv()))return false;
            }
            return true;
        }
        else{
                return true;
            }
    }
}
