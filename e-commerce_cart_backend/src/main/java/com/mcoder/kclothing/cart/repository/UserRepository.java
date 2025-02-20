package com.mcoder.kclothing.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mcoder.kclothing.cart.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

    boolean existsByEmail(String email);

    User findByEmail(String email);

}
