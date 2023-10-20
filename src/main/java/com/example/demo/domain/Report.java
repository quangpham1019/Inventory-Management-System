package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
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

    private String user;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private double price;
}
