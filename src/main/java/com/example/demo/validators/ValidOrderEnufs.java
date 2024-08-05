package com.example.demo.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {OrderEnufsValidator.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidOrderEnufs {

    String message() default "There are not enough parts/products for this order.";
    Class<?> [] groups() default {};
    Class<? extends Payload> [] payload() default {};
}
