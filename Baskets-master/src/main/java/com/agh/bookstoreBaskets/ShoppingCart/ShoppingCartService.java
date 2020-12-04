package com.agh.bookstoreBaskets.ShoppingCart;


public interface ShoppingCartService {
    public ShoppingCart getShoppingCart(String cartId);

    public ShoppingCart createNewShoppingCart();

    public ShoppingCart removeItem(String cartId, Long itemId);

    public ShoppingCart addItem(String cartId, Long prodId, int quantity);
}
