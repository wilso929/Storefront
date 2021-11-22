package com.example.b07project;

public class Owner extends User {

    String store_name;

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
}
