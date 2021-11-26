package com.example.b07project;

import java.util.LinkedList;

public class Order {
    String username;
    LinkedList<Product> products;
    double price;
    boolean completed;

    public Order(String username, LinkedList<Product> products, double price, boolean completed) {
        this.username = username;
        this.products = products;
        this.price = price;
        this.completed = completed;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LinkedList<Product> getProducts() {
        return products;
    }

    public void setProducts(LinkedList<Product> products) {
        this.products = products;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
