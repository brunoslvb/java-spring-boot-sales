package br.com.sales.domain.repository;

import br.com.sales.domain.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CustomerRepository {

    private EntityManager entityManager;

    @Autowired
    public CustomerRepository(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Transactional
    public Customer save(Customer customer){
        entityManager.persist(customer);
        return customer;
    }

    @Transactional
    public Customer update(Customer customer){
        entityManager.merge(customer);
        return customer;
    }

    @Transactional
    public void delete(Customer customer){
        if(!entityManager.contains(customer))
            customer = entityManager.merge(customer);
        entityManager.remove(customer);
    }

    @Transactional
    public void delete(Integer id){
        entityManager.remove(entityManager.find(Customer.class, id));
    }

    @Transactional(readOnly = true)
    public List<Customer> getByName(String name){
        String jpql = "SELECT c FROM Customer c WHERE c.name LIKE :name";
        TypedQuery<Customer> typedQuery = entityManager.createQuery(jpql, Customer.class);
        typedQuery.setParameter("name", "%" + name + "%");
        return typedQuery.getResultList();
    }

    @Transactional(readOnly = true)
    public List<Customer> getAll(){
        return entityManager.createQuery("FROM Customer", Customer.class).getResultList();
    }
}
