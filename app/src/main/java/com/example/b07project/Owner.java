package com.example.b07project;

import android.os.Parcelable;
import android.os.Parcel;

import java.util.HashSet;
import java.util.ArrayList;

public class Owner extends User implements Parcelable {
    // Use Aydin's Owner class not this one

    String store_name;
    ArrayList<Product> product_list = new ArrayList<>();
    HashSet<Order> orders = new HashSet<>();

    public Owner() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Owner(String username, String password, String store_name) {
        super(username, password);
        this.store_name = store_name;
    }

    protected Owner(Parcel in) {
        store_name = in.readString();
    }

    public static final Creator<Owner> CREATOR = new Creator<Owner>() {
        @Override
        public Owner createFromParcel(Parcel in) {
            return new Owner(in);
        }

        @Override
        public Owner[] newArray(int size) {
            return new Owner[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(store_name);
    }
}
