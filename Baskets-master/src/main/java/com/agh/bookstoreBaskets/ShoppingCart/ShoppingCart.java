package com.agh.bookstoreBaskets.ShoppingCart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

@Data
@Entity
@Table(name = "shoppingcart")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @JsonIgnore
    private String cartId;

    public ShoppingCart() {
        this.cartTotal = new BigDecimal(0);
    }
    private BigDecimal cartTotal;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cart_id")
    private List<ShoppingCartItem> itemList;


    public void addShoppingCartItem(ShoppingCartItem cartItem) {
        itemList.add(cartItem);
    }

    public void removeShoppingCartItem(ShoppingCartItem cartItem) {
        itemList.remove(cartItem);
    }

    public void setNewShoppingCartItemList(List<ShoppingCartItem> list){
        itemList.removeAll(itemList);
        itemList.addAll(list);
    }
    public void recalculateCart(){
        Function<ShoppingCartItem, BigDecimal> totalMapper = prod -> prod.getData().getPrice().multiply(BigDecimal.valueOf(prod.getProductQuantity()));
        cartTotal = itemList.stream()
                .map(totalMapper)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
