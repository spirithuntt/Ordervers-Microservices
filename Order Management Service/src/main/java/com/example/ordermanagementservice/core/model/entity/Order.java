package com.example.ordermanagementservice.core.model.entity;

import com.example.ordermanagementservice.core.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private int quantity;

    private OrderStatus orderStatus;




}
