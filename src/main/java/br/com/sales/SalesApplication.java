package br.com.sales;

import br.com.sales.domain.entity.Customer;
import br.com.sales.domain.entity.Order;
import br.com.sales.domain.repository.CustomerRepository;
import br.com.sales.domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
@RestController
public class SalesApplication {

    @Bean
    public CommandLineRunner init(
        @Autowired CustomerRepository customerRepository,
        @Autowired OrderRepository orderRepository
    ){
        return args -> {
            Customer customer = new Customer("Bruno");
            customerRepository.save(customer);

            Order order = new Order();
            order.setCustomer(customer);
            order.setOrderDate(LocalDate.now());
            order.setTotal(BigDecimal.valueOf(100));

            orderRepository.save(order);

            Customer customerFound = customerRepository.findCustomerFetchOrders(customer.getId());

            System.out.println(customerFound);
            System.out.println(customerFound.getOrders());

            orderRepository.findByCustomer(customer).forEach(System.out::println);


        };
    }

    public static void main(String ...args){
        SpringApplication.run(SalesApplication.class, args);
    }
}
