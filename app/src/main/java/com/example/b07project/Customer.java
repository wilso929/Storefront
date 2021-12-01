package com.example.b07project;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Customer extends User implements Parcelable {

    ArrayList<Order> orders = new ArrayList<>();

    public Customer() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Customer(String username, String password) {
        super(username, password);
    }

    protected Customer(Parcel in) {
        // read data in the same order data was written in writeToParcel
        super(in.readString(), in.readString()); // reads first two strings written to the parcel
        this.orders = new ArrayList<>();
        Object[] tmpOrders = in.readArray(Order.class.getClassLoader());

        for (int i = 0; i < tmpOrders.length; i++) {
            if (tmpOrders[i] instanceof Order) {
                this.orders.add((Order) tmpOrders[i]);
            }
        }
    }

    public static final Creator<Customer> CREATOR = new Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };

    public void add_order(Order o){
        orders.add(o);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.getUsername());
        parcel.writeString(this.getPassword());
        parcel.writeArray(this.orders.toArray());
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = new ArrayList<>();

        if (orders != null) {
            this.orders = orders;
        }
    }
}