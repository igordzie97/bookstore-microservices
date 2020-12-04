package com.agh.bookstoreOrders.DataTransferObjects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.List;

public class ShoppingCartDTO {

    @JsonIgnore
    private Long id;

    @JsonIgnore
    private String cartId;

    public ShoppingCartDTO() {

    }

    private BigDecimal cartTotal;

    private List<CartProductDTO> itemList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public BigDecimal getCartTotal() {
        return cartTotal;
    }

    public void setCartTotal(BigDecimal cartTotal) {
        this.cartTotal = cartTotal;
    }

    public List<CartProductDTO> getItemList() {
        return itemList;
    }

    public void setItemList(List<CartProductDTO> itemList) {
        this.itemList = itemList;
    }
}
