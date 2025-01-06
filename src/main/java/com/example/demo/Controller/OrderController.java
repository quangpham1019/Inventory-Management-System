package com.example.demo.Controller;

import com.example.demo.Domain.Order;
import com.example.demo.Repository.CRUDRepository.OrderRepository;
import com.example.demo.Service.Interface.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/orderList")
    public String getOrderList(Model model,
                               @Param("orderKeyword") String orderKeyword,
                               @Param("filterCriteria") String filterCriteria) {
        List<Order> orderFromRepos = orderService.findAllBy(filterCriteria, orderKeyword);

        model.addAttribute("orderFromRepos", orderFromRepos);
        model.addAttribute("orderKeyword", orderKeyword);
        return "menu pages/orders";

    }
}
