package com.example.b07project;

import java.io.Serializable;

public class Product implements Serializable {
    String name;
    String brand;
    double price;

    public Product(){
    }

    public Product(String name, String brand, double price){
        this.name = name;
        this.brand = brand;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Name: " + name + " | Brand: " + brand + " | Price: $" + price;
    }
}
