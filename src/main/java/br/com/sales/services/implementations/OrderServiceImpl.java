package br.com.sales.services.implementations;

import br.com.sales.api.dtos.OrderDTO;
import br.com.sales.domain.entities.Order;
import br.com.sales.domain.repositories.OrderRepository;
import br.com.sales.services.OrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @Override
    public Order save(OrderDTO orderDTO) {
        return null;
    }
}
