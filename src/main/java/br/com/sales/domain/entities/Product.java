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
    @NotEmpty(message = "Field 'description' is required")
    private String description;

    @Column(name = "unit_price")
    @NotNull(message = "Field 'unitPrice' is required")
    private BigDecimal unitPrice;

}
