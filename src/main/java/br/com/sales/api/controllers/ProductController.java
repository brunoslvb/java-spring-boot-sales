package br.com.sales.api.controllers;

import br.com.sales.domain.entities.Product;
import br.com.sales.domain.repositories.ProductRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductRepository productRepository;

    public ProductController(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product save(@RequestBody Product product){
        return productRepository.save(product);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Product update(@PathVariable Integer id, @RequestBody Product product){
        return productRepository.findById(id).map(productFound -> {
            product.setId(productFound.getId());
            return productRepository.save(product);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        productRepository
            .findById(id)
            .map(customerFound -> {
                productRepository.delete(customerFound);
                return customerFound;
            })
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Integer id){
        return productRepository
            .findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
    }

    @GetMapping
    public List<Product> find(Product filter){
        ExampleMatcher matcher = ExampleMatcher
            .matching()
            .withIgnoreCase()
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filter, matcher);

        return productRepository.findAll(example);

    }

}
