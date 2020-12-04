package com.agh.bookstoreOrders.OrderProduct;


import com.agh.bookstoreOrders.Order.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Cascade;


import javax.persistence.*;
import javax.transaction.Transactional;
import java.math.BigDecimal;
@Data
@Entity
@Table(name = "orderproducts")
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderProductId;

    private Long id; //application product_id

    @JsonIgnore
    private Long order_id;

    private Long productQuantity;

    private String name;

    private String description;

    private BigDecimal price;

    public OrderProduct(){}
}