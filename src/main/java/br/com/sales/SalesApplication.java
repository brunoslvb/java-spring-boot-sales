package br.com.sales;

import br.com.sales.domain.entities.Customer;
import br.com.sales.domain.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SalesApplication {
    public static void main(String ...args){
        SpringApplication.run(SalesApplication.class, args);
    }
}
