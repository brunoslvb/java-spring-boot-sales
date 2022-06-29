package br.com.sales.domain.repository;

import br.com.sales.domain.entity.Customer;
import br.com.sales.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByCustomer(Customer customer);

}
