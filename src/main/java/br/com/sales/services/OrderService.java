package br.com.sales.services;

import br.com.sales.api.dtos.OrderDTO;
import br.com.sales.domain.entities.Order;

public interface OrderService {

    Order save(OrderDTO orderDTO);

}
