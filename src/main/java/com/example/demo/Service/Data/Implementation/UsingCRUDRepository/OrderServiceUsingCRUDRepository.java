package com.example.demo.Service.Data.Implementation.UsingCRUDRepository;

import com.example.demo.Domain.Item;
import com.example.demo.Domain.Order;
import com.example.demo.Domain.OrderItem;
import com.example.demo.Domain.PaymentMethod;
import com.example.demo.Repository.CRUDRepository.OrderRepository;
import com.example.demo.Service.Data.Interface.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceUsingCRUDRepository extends CommonServiceUsingCRUDRepository<Order, Long> implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceUsingCRUDRepository(OrderRepository orderRepository) {
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

    @Override
    public void addItemToOrder(Order order, Item currentItem) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(currentItem);

        // COMPARE orderItem with elements of order.getOrderItemSet()
        if(!order.getOrderItemSet().contains(orderItem)) {
            // orderItem NOT exists in the orderItemSet
            orderItem.setQuantity(1);
            order.getOrderItemSet().add(orderItem);
        } else {
            // orderItem DOES exist in the orderItemSet
            OrderItem orderItemInOrder = order.getOrderItemSet()
                    .stream()
                    .filter(o -> o.equals(orderItem))
                    .findFirst().get();
            orderItemInOrder.setQuantity(orderItemInOrder.getQuantity() + 1);
        }
        order.setTotalPrice(order.getTotalPrice() + orderItem.getItem().getPrice());
    }
}
