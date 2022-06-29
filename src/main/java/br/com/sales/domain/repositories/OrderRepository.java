package br.com.sales.domain.repositories;

import br.com.sales.domain.entities.Customer;
import br.com.sales.domain.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByCustomer(Customer customer);

}
