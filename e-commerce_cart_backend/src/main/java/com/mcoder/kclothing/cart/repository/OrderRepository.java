package com.mcoder.kclothing.cart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mcoder.kclothing.cart.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

    List<Order> findByUserId(Long userId);

}
