package com.example.orderStateManagement.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public enum OrderStatus {
    CREATED,
    CONFIRMED,
    SHIPPED,
    DELIVERED,
    CANCELLED;


    private static final Map<OrderStatus, Set<OrderStatus>> ALLOWED_TRANSITIONS = new HashMap<>();

    static {
        ALLOWED_TRANSITIONS.put(
                CREATED,Set.of(CONFIRMED,CANCELLED)
        );
        ALLOWED_TRANSITIONS.put(
                CONFIRMED,Set.of(SHIPPED,CANCELLED)

        );
        ALLOWED_TRANSITIONS.put(
                SHIPPED,Set.of(DELIVERED)
        );
        ALLOWED_TRANSITIONS.put(
                DELIVERED,Set.of()
        );
        ALLOWED_TRANSITIONS.put(
                CANCELLED,Set.of()
        );
    }

    public boolean canTransitionTo(OrderStatus nextStatus){
       if(nextStatus == null) {
           return false;
       }

        Set<OrderStatus> allowedStatuses =  ALLOWED_TRANSITIONS.get(this);
        return allowedStatuses.contains(nextStatus);
    }

}



















