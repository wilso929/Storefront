package com.example.b07project;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class Owner extends User  implements Serializable, Parcelable {

    String store_name;
    ArrayList<Product> product_list = new ArrayList<>();
    ArrayList<Order> orders = new ArrayList<>();

    public static final Creator<Owner> CREATOR = new Creator<Owner>() {
        // came from implementing Parcelable interface
        @Override
        public Owner createFromParcel(Parcel in) {
            return new Owner(in);
        }

        @Override
        public Owner[] newArray(int size) {
            return new Owner[size];
        }
    };

    public Owner() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public Owner(String username, String password, String store_name) {
        super(username, password);
        this.store_name = store_name;
        this.product_list.clear();
        this.orders.clear();
    }

    protected Owner(Parcel in) {
    } // needed for implementing Parcelable

    public ArrayList<Product> getProduct_list() {
        return product_list;
    }

    public void setProduct_list(ArrayList<Product> product_list) {
        this.product_list.clear();
        this.product_list = product_list;
    }

    public boolean productExists(Product p){
        for (Product a: product_list){
            if (a.equals(p)){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = new ArrayList<>();

        if (orders != null) {
            this.orders.addAll(orders);
        }
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

    @Override // from implementing Parcelable, idk what this does
    public int describeContents() {
        return 0;
    }

    @Override // from implementing Parcelable, idk what this does
    public void writeToParcel(Parcel parcel, int i) {}
}
