package br.com.sales.services.implementations;

import br.com.sales.api.dtos.OrderDTO;
import br.com.sales.api.dtos.OrderItemDTO;
import br.com.sales.domain.entities.Customer;
import br.com.sales.domain.entities.Order;
import br.com.sales.domain.entities.OrderItem;
import br.com.sales.domain.entities.Product;
import br.com.sales.domain.enums.OrderStatus;
import br.com.sales.domain.repositories.CustomerRepository;
import br.com.sales.domain.repositories.OrderItemRepository;
import br.com.sales.domain.repositories.OrderRepository;
import br.com.sales.domain.repositories.ProductRepository;
import br.com.sales.exceptions.BusinessRuleException;
import br.com.sales.exceptions.OrderNotFoundException;
import br.com.sales.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    @Transactional // Caso alguma operação dentro dessa função não seja executada com sucesso, um rollback será feito
    public Order save(OrderDTO orderDTO) {

        Integer customerId = orderDTO.getCustomer();
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new BusinessRuleException("Invalid customer code"));

        Order order = new Order();
        order.setTotal(orderDTO.getTotal());
        order.setOrderDate(LocalDate.now());
        order.setCustomer(customer);
        order.setStatus(OrderStatus.DONE);

        List<OrderItem> orderItems = convertItems(order, orderDTO.getItems());

        orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);
        order.setItems(orderItems);

        return order;
    }

    @Override
    public Optional<Order> getFullOrder(Integer id) {
        return orderRepository.findByIdFetchItems(id);
    }

    @Override
    @Transactional
    public void updateStatus(Integer id, OrderStatus status) {
        orderRepository.findById(id).map(order -> {
            order.setStatus(status);
            return orderRepository.save(order);
        }).orElseThrow(() -> new OrderNotFoundException());
    }

    private List<OrderItem> convertItems(Order order, List<OrderItemDTO> items){
        if(items.isEmpty())
            throw new BusinessRuleException("Is not possible save order without items");

        return items.stream().map(item -> {
            Product product = productRepository.findById(item.getProduct()).orElseThrow(() -> new BusinessRuleException("Invalid product code"));
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(item.getQuantity());
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            return orderItem;
        }).collect(Collectors.toList());

    }
}
