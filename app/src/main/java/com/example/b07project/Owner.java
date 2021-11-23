package com.example.b07project;

import java.util.HashSet;
import java.util.LinkedHashSet;

public class Owner extends User {

    String store_name;
    LinkedHashSet<Product> product_list;
    HashSet<Order> orders;

    public Owner() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Owner(String username, String password, String store_name) {
        super(username, password);
        this.store_name = store_name;

    }

    public String getStore_name(){
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public void add_product(Product p) {
        product_list.add(p);
    }

    public void add_product(String name, String brand, double price){
        Product p = new Product(name, brand, price);
        product_list.add(p);
    }

    public void complete_order(Order o){
        for (Order p: orders){
            if (o.equals(p))
                p.completed = true;
            //Alert customer that their order is complete
            //search database for customer, find customer order, mark as completed
        }
    }
}
