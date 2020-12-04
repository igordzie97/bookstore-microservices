package com.agh.bookstoreOrders.DataTransferObjects;

public class StockUpdateDTO {

    private Long productId;
    private Long stockToReduce;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getStockToReduce() {
        return stockToReduce;
    }

    public void setStockToReduce(Long stockToReduce) {
        this.stockToReduce = stockToReduce;
    }
}
