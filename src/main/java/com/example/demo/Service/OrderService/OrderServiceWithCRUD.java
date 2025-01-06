package com.example.demo.Service.OrderService;

import com.example.demo.Domain.Order;
import com.example.demo.Domain.PaymentMethod;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Service.CommonService.CommonServiceWithCRUD;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceWithCRUD extends CommonServiceWithCRUD<Order, Long> implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceWithCRUD(OrderRepository orderRepository) {
        super(orderRepository);
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> findAllBy(String filterCriteria, String orderKeyword) {
        if (filterCriteria == null || filterCriteria.isEmpty()) { filterCriteria = "";}
        return switch (filterCriteria) {
            case "paymentMethod" -> orderRepository.findAllByPaymentMethod(PaymentMethod.valueOf(orderKeyword));
            case "customer" -> orderRepository.findAllByCustomer_LastName(orderKeyword);
            default -> (List<Order>) orderRepository.findAll();
        };
    }
}
