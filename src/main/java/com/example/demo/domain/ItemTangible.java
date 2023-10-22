package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.Min;

@Entity
@Getter
@Setter
@Table(name = "items_tangible")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ItemTangible extends Item {
    @Min(value = 0, message = "Inventory value must be positive")
    private int inv;

    public ItemTangible() {}
    public ItemTangible(String name, double price, int inv) {
        super(name, price);
        this.inv = inv;
    }
}
