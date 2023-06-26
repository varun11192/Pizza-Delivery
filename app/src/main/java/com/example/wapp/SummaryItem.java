package com.example.wapp;

public class SummaryItem {
    private String orderId;
    private String itemId;
    private String name;
    private double price;
    private String status;
    private byte[] imageBytes;

    public SummaryItem(String orderId, String itemId, String name, double price, String status, byte[] imageBytes) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.status = status;
        this.imageBytes = imageBytes;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }
}

