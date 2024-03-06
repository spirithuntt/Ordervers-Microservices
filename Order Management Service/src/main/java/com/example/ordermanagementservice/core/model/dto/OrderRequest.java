package com.example.ordermanagementservice.core.model.dto;

import com.example.ordermanagementservice.core.model.entity.Order;
import com.example.ordermanagementservice.core.model.enums.OrderStatus;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderRequest {

    private Long id;

    private Long productId;
    private int quantity;

    private int orderStatus;



    public Order toEntity() {
        return Order.builder()
                .id(this.id)
                .productId(this.productId)
                .quantity(this.quantity)
                .orderStatus(OrderStatus.values()[this.orderStatus])
                .build();
    }
}
