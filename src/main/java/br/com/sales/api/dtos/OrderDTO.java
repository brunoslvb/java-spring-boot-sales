package br.com.sales.api.dtos;

import br.com.sales.validations.NotEmptyList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    @NotNull(message = "Field 'customer' is required")
    private Integer customer;

    @NotNull(message = "Field 'total' is required")
    private BigDecimal total;

    @NotEmptyList(message = "Order cannot be save without items")
    private List<OrderItemDTO> items;

}
