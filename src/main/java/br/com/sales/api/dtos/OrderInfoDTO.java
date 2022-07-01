package br.com.sales.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderInfoDTO {

    private Integer code;
    private String cpf;
    private String customerName;
    private BigDecimal total;
    private String orderDate;
    private String status;
    private List<OrderItemInfoDTO> items;

}
