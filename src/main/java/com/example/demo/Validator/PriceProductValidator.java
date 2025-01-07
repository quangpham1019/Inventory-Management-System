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
public class PriceProductValidator implements ConstraintValidator<ValidProductPrice, Product> {
    @Autowired
    private ApplicationContext context;

    public static  ApplicationContext myContext;

    @Override
    public void initialize(ValidProductPrice constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Product product, ConstraintValidatorContext constraintValidatorContext) {
        if(context==null) return true;
        if(context!=null)myContext=context;
        ProductService repo = myContext.getBean(ProductServiceWithCRUD.class);
        double sumPartsPrice = 0;
        if (product.getId() != 0) {
            Product myProduct = repo.findById((long) product.getId());
            for (Part p : myProduct.getParts()) sumPartsPrice = sumPartsPrice + p.getPrice();
            if (product.getPrice() >= sumPartsPrice) {
                return true;
            } else {
                return false;
            }
        }
        else {
            return true;
        }
    }
}
