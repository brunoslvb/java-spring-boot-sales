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

    @NotNull(message = "{field.order.customer-code.required}")
    private Integer customer;

    @NotNull(message = "{field.order.total.required}")
    private BigDecimal total;

    @NotEmptyList(message = "{field.order.items.required}")
    private List<OrderItemDTO> items;

}
