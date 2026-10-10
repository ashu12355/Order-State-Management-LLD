package com.example.orderStateManagement.entity;

import com.example.orderStateManagement.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String userId;
    @NotNull
    private String productId;
    @NotNull
    private Long quantity;
    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
