package com.example.b07project;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.jar.Attributes;

public class Order implements Serializable {
    String customer;
    ArrayList<Product> products = new ArrayList<Product>();
    String owner;
    boolean completed;

    public Order(){

    }

    public Order(String customer, String owner, ArrayList<Product> products, boolean completed) {
        this.customer = customer;
        this.products = products;
        this.completed = completed;
        this.owner = owner;
    }

    public double calculate_price(){
        double sum = 0;
        for (Product p: products){
            sum += p.price;
        }
        return sum;
    }

    @NonNull
    @Override
    public String toString(){
        String end = "Name: " + customer + "\n";
        if (completed)
            end += "Completed\n";
        else
            end += "Not Completed\n";
        for (Product p: products){
            end += "\n" + p.toString();
        }
        return end;
    }


    public String getCustomer() {
        return customer;
    }
    public String getOwner() {
        return owner;
    }
    public void setCustomer(String customer) {
        this.customer = customer;
    }
    public void setOwner(String owner) {
        this.owner = owner;
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
