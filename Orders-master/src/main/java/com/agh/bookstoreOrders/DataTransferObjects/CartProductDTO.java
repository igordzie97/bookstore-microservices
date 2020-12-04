package com.agh.bookstoreOrders.DataTransferObjects;

import java.math.BigDecimal;

public class CartProductDTO {
    private Long id;

    private Long productQuantity;

    private ProductDTO data;

    public Long getId() {
        return id;
    }

    public Long getProductQuantity() {
        return productQuantity;
    }

    public ProductDTO getData() {
        return data;
    }

    public void setProductQuantity(Long productQuantity) {
        this.productQuantity = productQuantity;
    }

    public void setData(ProductDTO data) {
        this.data = data;
    }
}
