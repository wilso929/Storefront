package com.example.b07project;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashSet;

public class Customer extends User implements Parcelable {

    HashSet<Order> orders = new HashSet<>();

    public Customer() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Customer(String username, String password) {
        super(username, password);
    }

    protected Customer(Parcel in) {
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
        // do nothing here
    }
}
