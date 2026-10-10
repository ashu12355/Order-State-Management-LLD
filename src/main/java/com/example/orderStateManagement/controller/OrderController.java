package com.example.orderStateManagement.controller;

import com.example.orderStateManagement.dtos.UpdateOrderStatusRequest;
import com.example.orderStateManagement.entity.Order;
import com.example.orderStateManagement.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders/{orderId}/status")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PutMapping
    public Order updateOrderStatus(@PathVariable("orderId")Long orderId,
                                   @RequestBody UpdateOrderStatusRequest request) {

        return orderService.updateStatus(orderId, request.status());

    }


}
