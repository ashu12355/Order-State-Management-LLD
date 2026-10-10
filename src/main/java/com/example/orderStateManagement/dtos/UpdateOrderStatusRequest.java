package com.example.orderStateManagement.dtos;

import com.example.orderStateManagement.enums.OrderStatus;
import lombok.Data;

public record UpdateOrderStatusRequest(
        Long orderId,
        OrderStatus status
) {
}
