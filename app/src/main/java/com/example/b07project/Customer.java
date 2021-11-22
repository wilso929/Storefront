package com.example.b07project;

public class Customer extends User{

    public Customer() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Customer(String username, String password) {

        super(username, password);
    }
}
