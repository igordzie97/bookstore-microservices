package com.agh.bookstoreOrders.Order;


import com.agh.bookstoreOrders.OrderProduct.OrderProduct;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private BigDecimal cartTotal;

    private BigDecimal paid;

    //private shippingType;  //TODO :(
    //private paymentType;

    private Long userId;

    private OrderMode orderMode;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<OrderProduct> itemList;

    public Order() {
        itemList = new LinkedList<>();
        orderMode = OrderMode.WITHOUTREGISTER;
    }

    public void addItem(OrderProduct prod){
        itemList.add(prod);
    }


    public void setCartTotal(BigDecimal cartTotal) {
        this.cartTotal = cartTotal;
    }

    public void setPaid(BigDecimal paid) {
        this.paid = paid;
    }

    private void setItemList(List<OrderProduct> itemList) {
        this.itemList = itemList;
    }
}