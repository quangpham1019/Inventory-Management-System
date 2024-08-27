package com.example.demo.Domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 *
 *
 *
 *
 */
@Entity
@DiscriminatorValue("2")
public class OutsourcedPart extends Part{
    String companyName;

    public OutsourcedPart() {
    }

    public OutsourcedPart(String companyName, String name, double price, int inv, int minInv, int maxInv) {
        super(name, price, inv, minInv, maxInv);
        this.companyName = companyName;
    }
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
