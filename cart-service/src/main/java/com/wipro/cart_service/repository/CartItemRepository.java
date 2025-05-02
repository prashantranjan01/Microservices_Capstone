package com.wipro.cart_service.repository;


import com.wipro.cart_service.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteByCartIdAndProductId(String cartId, String productId);

    Optional<CartItem> findByCartIdAndProductId(String cartId, String productId);

    List<CartItem> findByCartId(String cartId);

    void deleteByCartId(String id);
}