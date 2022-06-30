package br.com.sales.api.controllers;

import br.com.sales.domain.entities.Customer;
import br.com.sales.domain.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/customers")
public class CustomerController {

    private CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity getCustomerById(@PathVariable Integer id){
        Optional<Customer> customerFound = customerRepository.findById(id);

        if(customerFound.isPresent())
            return ResponseEntity.ok(customerFound.get());

        return ResponseEntity.notFound().build();

    }

    @PostMapping("")
    @ResponseBody
    public ResponseEntity save(@RequestBody Customer customer){
        return ResponseEntity.ok(customerRepository.save(customer));
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity delete(@PathVariable Integer id){
        Optional<Customer> customerFound = customerRepository.findById(id);

        if(customerFound.isPresent()) {
            customerRepository.delete(customerFound.get());
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity update(@PathVariable Integer id, @RequestBody Customer customer){

        return customerRepository.findById(id).map(customerFound -> {
            customer.setId(customerFound.getId());
            customerRepository.save(customer);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping()
    @ResponseBody
    public ResponseEntity find(Customer filter){
        ExampleMatcher matcher = ExampleMatcher
            .matching()
            .withIgnoreCase()
            .withStringMatcher(ExampleMatcher.StringMatcher.EXACT);

        Example example = Example.of(filter, matcher);

        List<Customer> customers = customerRepository.findAll(example);

        return ResponseEntity.ok(customers);
    }

}
