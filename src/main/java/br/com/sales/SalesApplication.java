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

            System.out.println(customerRepository.existsByName("Bruno"));
            System.out.println(customerRepository.existsByName("Teste"));

            List<Customer> customersFound = customerRepository.encontrarPorNome("Bruno");
            customersFound.forEach(System.out::println);

            List<Customer> customersFoundSqlNative = customerRepository.encontrarPorNomeNativo("Bruno");
            customersFoundSqlNative.forEach(System.out::println);

            customerRepository.deletaPorNome("Bruno");

            List<Customer> newCustomerList = customerRepository.findAll();
            newCustomerList.forEach(System.out::println);

        };
    }

    public static void main(String ...args){
        SpringApplication.run(SalesApplication.class, args);
    }
}
