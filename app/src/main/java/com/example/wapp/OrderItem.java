package com.example.wapp;

public class OrderItem {
    private String itemId;
    private String name;
    private double price;
    private byte[] imageBytes;

    public OrderItem() {
        // Required empty constructor for Firestore
    }

    public OrderItem(String itemId, String name, double price, byte[] imageBytes) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.imageBytes = imageBytes;
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

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }
}

