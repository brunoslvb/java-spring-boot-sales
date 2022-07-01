package br.com.sales.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemInfoDTO {

    private String productDescription;
    private BigDecimal unitPrice;
    private Integer quantity;

}
