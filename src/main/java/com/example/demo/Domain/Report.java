package com.example.demo.Domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "reports")
@ToString
public class Report {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    private Order order;

    @CreationTimestamp
    private Date createdAt;

    private String username;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private double price;

    public Report() {}
    public Report(Order order, String username, Customer customer, double price) {
        this.order = order;
        this.username = username;
        this.customer = customer;
        this.price = price;
    }
}
