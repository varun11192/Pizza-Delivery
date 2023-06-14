package com.example.wapp.adapter;

import com.example.wapp.CartActivity;

public class CartItem {
    private String itemId;
    private String name;
    private double price;
    private byte[] imageBytes;

    public CartItem(){

    }

    public CartItem(String itemId, String name, double price, byte[] imageBytes) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.imageBytes = imageBytes;
    }

    public String getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }
}
