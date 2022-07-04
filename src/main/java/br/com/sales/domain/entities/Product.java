package br.com.sales.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "description")
    @NotEmpty(message = "{field.product.description.required}")
    private String description;

    @Column(name = "unit_price")
    @NotNull(message = "{field.product.unit-price.required}")
    private BigDecimal unitPrice;

}
