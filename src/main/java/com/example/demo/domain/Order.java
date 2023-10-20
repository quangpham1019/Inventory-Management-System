package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Component
@Entity
@Getter
@Setter
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Table(name = "orders")
@ToString
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    private String orderNumber;
    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<OrderItem> orderItemSet = new HashSet<>();


    public void addOrderItem(OrderItem orderItem) {

        if (orderItem != null) {
            orderItemSet.add(orderItem);
            System.out.println(this + " -- being added to order");
            orderItem.setOrder(this);
        }
    }

//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
//    @JsonIgnore
//    private Set<OrderItem> orderItemSet = new HashSet<>();
//
//    public void add(OrderItem orderItem) {
//
//        if (orderItem != null) {
//            orderItemSet.add(orderItem);
//            System.out.println("Adding order item to order");
//            orderItem.setOrder(this);
//            System.out.println("Setting order for order item");
//        }
//    }
}
