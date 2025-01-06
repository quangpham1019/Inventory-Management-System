package com.example.demo.Validator;

import com.example.demo.Domain.*;
import com.example.demo.Service.ProductService.ProductService;
import com.example.demo.Service.ProductService.ProductServiceWithCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OrderEnufsValidator implements ConstraintValidator<ValidOrderEnufs, Order> {

    @Autowired
    private ApplicationContext context;
    @Autowired
    private Order order;
    public static ApplicationContext myContext;

    @Override
    public void initialize(ValidOrderEnufs constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Order currentOrder, ConstraintValidatorContext constraintValidatorContext) {
        if (context == null) return true;
        if (context != null) myContext = context;

        ProductService productService = myContext.getBean(ProductServiceWithCRUD.class);
        for (OrderItem orderItem : order.getOrderItemSet()) {
            Item item = orderItem.getItem();
            if (item.getClass() != Service.class) {
                Product product = productService.findById((int) item.getId());
                System.out.println(product.getName());
            }


//            for (Part p : originalItemTangible.getParts()) {
//                if ((p.getInv()-p.getMinInv())<(product.getInv()-originalItemTangible.getInv()))return false;
//            }
//            return true;
//        }
//        else{
//            return true;
//        }
        }
        return true;
    }
}