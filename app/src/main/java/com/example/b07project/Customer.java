package com.example.b07project;

import java.util.HashSet;

public class Customer extends User{

    HashSet<Order> orders;

    public Customer() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
        this.orders = new HashSet<Order>();
    }

    public Customer(String username, String password) {

        super(username, password);
        this.orders = new HashSet<Order>();
    }

    public void add_order(Order o){
        orders.add(o);
    }
}
