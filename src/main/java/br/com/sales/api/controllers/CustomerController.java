package br.com.sales.api.controllers;

import br.com.sales.domain.entities.Customer;
import br.com.sales.domain.repositories.CustomerRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um cliente")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Cliente encontrado"),
        @ApiResponse(code = 404, message = "Cliente não encontrado")
    })
    public Customer getById(@PathVariable @ApiParam("Id do cliente") Integer id){
        return customerRepository
            .findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Salva um novo cliente")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Cliente salvo com sucesso"),
        @ApiResponse(code = 400, message = "Erro de validação")
    })
    public Customer save(@RequestBody @Valid Customer customer){
        return customerRepository.save(customer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        customerRepository
            .findById(id)
            .map(customerFound -> {
                customerRepository.delete(customerFound);
                return customerFound;
            })
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Customer update(@PathVariable Integer id, @RequestBody @Valid Customer customer){

        return customerRepository.findById(id).map(customerFound -> {
            customer.setId(customerFound.getId());
            return customerRepository.save(customer);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

    }

    @GetMapping
    public List<Customer> find(Customer filter){
        ExampleMatcher matcher = ExampleMatcher
            .matching()
            .withIgnoreCase()
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filter, matcher);

        return customerRepository.findAll(example);

    }

}
