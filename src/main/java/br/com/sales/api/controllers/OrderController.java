package br.com.sales.api.controllers;

import br.com.sales.api.dtos.OrderDTO;
import br.com.sales.domain.entities.Order;
import br.com.sales.domain.repositories.OrderRepository;
import br.com.sales.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody OrderDTO orderDTO){
        Order order = orderService.save(orderDTO);
        return order.getId();
    }


}
