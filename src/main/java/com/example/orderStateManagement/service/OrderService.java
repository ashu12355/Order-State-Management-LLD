package com.example.orderStateManagement.service;


import com.example.orderStateManagement.dtos.UpdateOrderStatusRequest;
import com.example.orderStateManagement.entity.Order;
import com.example.orderStateManagement.enums.OrderStatus;
import com.example.orderStateManagement.exception.InvalidTransitionException;
import com.example.orderStateManagement.exception.OrderNotFoundException;
import com.example.orderStateManagement.repository.OrderRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Order updateStatus(Long orderId, OrderStatus status) {

        Order existingOrder = orderRepository.findByIdWithLock(orderId).orElseThrow(
                () ->
                        new OrderNotFoundException("Order Not Found: " + orderId)

        );

        boolean transition = existingOrder.getStatus().canTransitionTo(status);

        if(!transition){
            throw new InvalidTransitionException("Invalid Transition : "+status);
        }


        existingOrder.setStatus(status);

        return orderRepository.save(existingOrder);
    }

}
