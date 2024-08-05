package com.example.demo.domain;

import com.example.demo.validators.ValidEnufParts;
import com.example.demo.validators.ValidProductPrice;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 *
 *
 *
 */
@Entity
@Table(name="Products")
@ValidProductPrice
@ValidEnufParts
public class Product extends Item implements Serializable {
    @Min(value = 0, message = "Inventory value must be positive")
    private int inv;

    @ManyToMany(cascade=CascadeType.ALL, mappedBy = "products")
    Set<Part> parts= new HashSet<>();

    public Product() {}

    public Product(String name, double price, int inv) {
        super(name, price);
        this.inv = inv;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getInv() {
        return inv;
    }

    public void setInv(int inv) {
        this.inv = inv;
    }

    public Set<Part> getParts() {
        return parts;
    }

    public void setParts(Set<Part> parts) {
        this.parts = parts;
    }

    public String toString(){
        return this.name;
    }

}
