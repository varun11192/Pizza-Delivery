package com.example.wapp;

import android.util.Base64;

import com.example.wapp.adapter.CartItem;

import java.util.ArrayList;
import java.util.List;
import android.util.Base64;

public class Order {
    private String userId;
    private String itemId;
    private String name;
    private double price;
    private String imageBytes; // Store as Base64-encoded string
    private double totalAmount;
    private String status;
    private String orderId;

    // Default constructor (required for Firestore)
    public Order() {
    }

    public Order(String userId, String itemId, String name, double price, byte[] imageBytes, double totalAmount, String status, String orderId) {
        this.userId = userId;
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.imageBytes = convertToBase64(imageBytes);
        this.totalAmount = totalAmount;
        this.status = status;
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
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

    public String getImageBytes() {
        return imageBytes;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public String getOrderId() {
        return orderId;
    }

    // Helper method to convert byte[] to Base64-encoded string
    private String convertToBase64(byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }
}
