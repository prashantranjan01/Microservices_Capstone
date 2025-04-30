package com.wipro.product_service.dto;


public class StockUpdateRequest {
    private int quantity;

    public StockUpdateRequest() {}

    public StockUpdateRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}