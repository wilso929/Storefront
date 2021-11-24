package com.example.b07project;

import java.util.HashSet;

public class Customer extends User{

    // Default constructor required for calls to DataSnapshot.getValue(User.class)
    HashSet<Order> orders = new HashSet<>();

    public Customer() {
    }

    public Customer(String username, String password) {
        super(username, password);
    }

    public void add_order(Order o){
        orders.add(o);
    }
}
