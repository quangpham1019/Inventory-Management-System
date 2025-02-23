package com.example.demo.Domain;

import com.example.demo.Validator.ValidOrderEnufs;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
@Entity
@Getter
@Setter
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Table(name = "orders")
@ToString
@ValidOrderEnufs
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @CreationTimestamp
    private Date createdAt;
    private PaymentMethod paymentMethod;
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
            orderItem.setOrder(this);
        }
    }
}
