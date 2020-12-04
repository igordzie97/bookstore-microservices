package com.agh.bookstoreBaskets.ShoppingCart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    List<ShoppingCart> findAll();
    ShoppingCart findById(Integer id);
    ShoppingCart findByCartId(String id);
}
