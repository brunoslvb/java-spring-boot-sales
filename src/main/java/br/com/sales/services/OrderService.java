package br.com.sales.services;

import br.com.sales.api.dtos.OrderDTO;
import br.com.sales.domain.entities.Order;
import br.com.sales.domain.enums.OrderStatus;

import java.util.Optional;

public interface OrderService {

    Order save(OrderDTO orderDTO);

    Optional<Order> getFullOrder(Integer id);

    void updateStatus(Integer id, OrderStatus status);

}
