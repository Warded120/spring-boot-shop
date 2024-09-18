package com.ivan.usercrud.service.order;

import com.ivan.usercrud.entity.Order;

import java.util.List;

public interface OrderService {
    Order save(Order order);
    List<Order> findByUsername(String username);
}
