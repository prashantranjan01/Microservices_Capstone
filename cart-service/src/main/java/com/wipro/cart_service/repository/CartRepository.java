package com.wipro.cart_service.repository;

import com.wipro.cart_service.entity.Cart;
import com.wipro.cart_service.entity.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
    Optional<Cart> findByUserIdAndStatus(String userId, CartStatus status);
}