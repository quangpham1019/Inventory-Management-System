package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "services")
public class Service extends Item {

    @CreationTimestamp
    private Date createdDate;
    private int duration;
    public Service() {}
    public Service(String name, double price, int duration) {
        super(name, price);
        this.duration = duration;
    }
}
