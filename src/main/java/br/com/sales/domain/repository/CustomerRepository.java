package br.com.sales.domain.repository;

import br.com.sales.domain.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CustomerRepository {

    private static String SELECT_ALL = "SELECT * FROM customer";
    private static String INSERT = "INSERT INTO customer (name) VALUES (?)";
    private static String UPDATE = "UPDATE customer SET name = ? WHERE id = ?";
    private static String DELETE = "DELETE FROM customer WHERE id = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomerRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public Customer save(Customer customer){
        jdbcTemplate.update(INSERT, customer.getName());
        return customer;
    }

    public Customer update(Customer customer){
        jdbcTemplate.update(UPDATE, customer.getName(), customer.getId());
        return customer;
    }

    public void delete(Customer customer){
        delete(customer.getId());
    }

    public void delete(Integer id){
        jdbcTemplate.update(DELETE, id);
    }

    public List<Customer> getByName(String name){
        return jdbcTemplate.query(SELECT_ALL.concat(" WHERE name LIKE ?"), getCustomerRowMapper(), "%" + name + "%");
    }

    public List<Customer> getAll(){
        return jdbcTemplate.query(SELECT_ALL, getCustomerRowMapper());
    }

    private RowMapper<Customer> getCustomerRowMapper() {
        return new RowMapper<Customer>() {
            @Override
            public Customer mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Customer(resultSet.getInt("id"), resultSet.getString("name"));
            }
        };
    }

}
