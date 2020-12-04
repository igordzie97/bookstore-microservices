package com.agh.bookstoreBaskets.ShoppingCart;

import com.agh.bookstoreBaskets.DataTransferObjects.ProductDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "shoppingCartItem")
public class ShoppingCartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    private Long cart_id;

    private Long productId;

    private Long productQuantity;

    @Transient
    private ProductDTO data;

    public ShoppingCartItem(){}

    public ShoppingCartItem(Long cart_id) {
        this.cart_id = cart_id;
        this.productQuantity = new Long(0);
    }
}
