package com.ivan.usercrud.service.order;

import com.ivan.usercrud.entity.Order;
import com.ivan.usercrud.repo.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findByUsername(String username) {
        return orderRepository.findByUser_UsernameOrderByDate(username);
    }
}
