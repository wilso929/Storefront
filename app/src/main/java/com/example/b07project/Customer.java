package com.example.b07project;

import java.io.Serializable;
import java.util.ArrayList;

public class Customer extends User implements Serializable {

    ArrayList<Order> orders = new ArrayList<Order>();

    public Customer() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Customer(String username, String password) {

        super(username, password);
    }

    public void add_order(Order o){
        orders.add(o);
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }
}
