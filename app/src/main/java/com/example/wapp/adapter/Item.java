package com.example.wapp.adapter;

public class Item {
    private String name;
    private double price;
    private int quantity;
    private byte[] imageBytes;

    public Item(String name, double price, int quantity, byte[] imageBytes) {
        this.name = name;
        this.price = price;
        this.quantity = 0;
        this.imageBytes = imageBytes;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }
}
