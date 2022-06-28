package br.com.sales;

import br.com.sales.domain.entity.Customer;
import br.com.sales.domain.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class SalesApplication {

    @Bean
    public CommandLineRunner init(@Autowired CustomerRepository customerRepository){
        return args -> {
            customerRepository.save(new Customer("Bruno"));
            customerRepository.save(new Customer("Bárbara"));

            System.out.println("Clientes: ");
            List<Customer> customers = customerRepository.findAll();
            customers.forEach(System.out::println);

            customers.forEach(customer -> {
                customer.setName(customer.getName() + " - Atualizado");
                customerRepository.save(customer);
            });

            System.out.println("Clientes atualizados: ");
            List<Customer> updatedCustomers = customerRepository.findAll();
            updatedCustomers.forEach(System.out::println);

            System.out.println("Buscando clientes: ");
            customerRepository.findByNameLike("ra").forEach(System.out::println);

            System.out.println("Deletando clientes: ");
            updatedCustomers.forEach(customer -> {
                customerRepository.delete(customer);
            });

            System.out.println("Clientes na base de dados: ");
            customerRepository.findAll().forEach(System.out::println);
        };
    }

    public static void main(String ...args){
        SpringApplication.run(SalesApplication.class, args);
    }
}
