package br.com.sales.api.controllers;

import br.com.sales.api.dtos.OrderDTO;
import br.com.sales.api.dtos.OrderInfoDTO;
import br.com.sales.api.dtos.OrderItemInfoDTO;
import br.com.sales.api.dtos.OrderStatusDTO;
import br.com.sales.domain.entities.Order;
import br.com.sales.domain.entities.OrderItem;
import br.com.sales.domain.enums.OrderStatus;
import br.com.sales.domain.repositories.OrderRepository;
import br.com.sales.exceptions.BusinessRuleException;
import br.com.sales.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
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

    @GetMapping("/{id}")
    public OrderInfoDTO getById(@PathVariable Integer id){
        return orderService.getFullOrder(id)
            .map(order -> convert(order))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@PathVariable Integer id, @RequestBody OrderStatusDTO orderStatusDTO){
        orderService.updateStatus(id, OrderStatus.valueOf(orderStatusDTO.getNewStatus()));
    }

    private OrderInfoDTO convert(Order order) {

        return OrderInfoDTO.builder()
            .code(order.getId())
            .customerName(order.getCustomer().getName())
            .orderDate(order.getOrderDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
            .cpf(order.getCustomer().getCpf())
            .total(order.getTotal())
            .status(order.getStatus().name())
            .items(convert(order.getItems()))
            .build();

    }

    private List<OrderItemInfoDTO> convert(List<OrderItem> items){
        if(CollectionUtils.isEmpty(items)){
            return Collections.emptyList();
        }

        return items.stream()
            .map(
                item -> OrderItemInfoDTO.builder()
                    .productDescription(item.getProduct().getDescription())
                    .unitPrice(item.getProduct().getUnitPrice())
                    .quantity(item.getQuantity())
                    .build()
            ).collect(Collectors.toList());
    }


}
