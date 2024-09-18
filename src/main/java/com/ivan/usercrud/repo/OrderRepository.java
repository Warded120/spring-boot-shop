package com.ivan.usercrud.repo;

import com.ivan.usercrud.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUser_UsernameOrderByDate(String username);
}
