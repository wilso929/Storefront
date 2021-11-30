package com.example.b07project;

import java.io.Serializable;
import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.LinkedHashSet;

public class Owner extends User  implements Serializable {


    String store_name;
    ArrayList<Product> product_list = new ArrayList<Product>();
    ArrayList<Order> orders = new ArrayList<Order>();

    public Owner() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public Owner(String username, String password, String store_name) {
        super(username, password);
        this.store_name = store_name;
        this.product_list.clear();
        this.orders.clear();
    }

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
        this.orders = orders;
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

    @Override
    public boolean equals(Object obj){
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() != this.getClass())
            return false;
        Owner other = (Owner)obj;
        if(!other.getUsername().equals(this.getUsername())){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode(){
        return this.getUsername().hashCode();
    }


}
