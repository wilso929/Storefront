package com.example.b07project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class Order implements Serializable {
    String username;
    ArrayList<Product> products;
    boolean completed;

    public Order(){

    }

    public Order(String username, ArrayList<Product> products, boolean completed) {
        this.username = username;
        this.products.clear();
        this.products = products;
        this.completed = completed;
    }

    public double calculate_price(){
        double sum = 0;
        for (Product p: products){
            sum += p.price;
        }
        return sum;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products.clear();
        this.products = products;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
