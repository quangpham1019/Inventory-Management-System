package com.example.demo.Domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "services")
public class JcsServicing extends Item {

    @CreationTimestamp
    private Date createdDate;
    private int duration;
    public JcsServicing() {}
    public JcsServicing(String name, double price, int duration) {
        super(name, price);
        this.duration = duration;
    }
}
