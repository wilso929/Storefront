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
        return "Item: " + name + " | Brand: " + brand + " | Price: $" + price;
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() != this.getClass())
            return false;
        Product p = (Product)obj;
        if (p.name.equals(name) && p.brand.equals(brand) && (price == p.price))
            return true;
        return false;
    }

}
