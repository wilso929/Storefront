package com.example.b07project;

import java.util.HashSet;

public class Customer extends User{

    HashSet<Order> orders = new HashSet<>();

    public Customer() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Customer(String username, String password) {
        super(username, password);
    }

    public void add_order(Order o){
        orders.add(o);
    }
}
