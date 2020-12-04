package com.agh.bookstoreOrders.DataTransferObjects;

import java.math.BigDecimal;

public class ProductDTO {
    private Long id;

    private String name;

    private int length;

    private int stock;

    private String description;

    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public int getStock() {
        return stock;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
