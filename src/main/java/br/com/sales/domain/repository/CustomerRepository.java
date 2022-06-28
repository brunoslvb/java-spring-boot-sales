package br.com.sales.domain.repository;

import br.com.sales.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    List<Customer> findByNameLike(String name);

    @Query(value = "SELECT c FROM Customer c WHERE c.name LIKE :name")
    List<Customer> encontrarPorNome(@Param("name") String name);

    @Query(value = "SELECT * FROM customer c WHERE c.name LIKE %:name%", nativeQuery = true)
    List<Customer> encontrarPorNomeNativo(@Param("name") String name);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Customer c WHERE c.name = :name")
    void deletaPorNome(@Param("name") String name);

    boolean existsByName(String name);
}
