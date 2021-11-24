package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayOwnerActivity extends AppCompatActivity {

    Owner owner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_owner);

        //Take in intent owner name
        Intent intent = getIntent();
        String owner_name = intent.getStringExtra("Key");
        this.owner = getOwner(owner_name);
    }

    public void view_products(View view) {
        Intent intent = new Intent(this, DisplayOwnerProductsActivity.class);
        intent.putExtra("owner", owner);
        startActivity(intent);
    }

    public void view_orders(View view){}


    public Owner getOwner(String owner_name){

        //Fields that need to be set
        ArrayList<Product> owner_prods = new ArrayList<>();
        ArrayList<Order> owner_orders = new ArrayList<>();
        ArrayList<Product> order_prods = new ArrayList<>();

        //Find Owner in database
        String username = owner_name;
        String password = "";
        String store_name = "";



        //Get username, password, store name
        DatabaseReference ref_owner = FirebaseDatabase.getInstance().getReference("Owners").child(owner_name);
        ref_owner.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String password = snapshot.child("password").getValue(String.class);
                    String store_name = snapshot.child("store_name").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        //Get Product Arraylist
        DatabaseReference ref_products = FirebaseDatabase.getInstance().getReference("Owners").child(owner_name).child("product_list");
        ref_products.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    owner_prods.clear();
                    for (DataSnapshot ds: snapshot.getChildren()){
                        String prod_name = ds.child("name").getValue(String.class);
                        String prod_brand = ds.child("brand").getValue(String.class);
                        double prod_price = ds.child("price").getValue(Double.class);
                        Product p = new Product(prod_name, prod_brand, prod_price);
                        owner_prods.add(p);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Get Product Arraylist
        DatabaseReference ref_orders = FirebaseDatabase.getInstance().getReference("Owners").child(owner_name).child("orders");
        ref_orders.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    owner_orders.clear();
                    for (DataSnapshot ds: snapshot.getChildren()){
                        order_prods.clear();
                        for (DataSnapshot d: ds.child("products").getChildren()){
                            String prod_name = ds.child("name").getValue(String.class);
                            String prod_brand = ds.child("brand").getValue(String.class);
                            double prod_price = ds.child("price").getValue(Double.class);
                            Product p = new Product(prod_name, prod_brand, prod_price);
                            order_prods.add(p);
                        }
                        String username = ds.child("username").getValue(String.class);
                        boolean completed = ds.child("completed").getValue(Boolean.class);
                        Order o = new Order(username, order_prods, completed);
                        owner_orders.add(o);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //return new Owner object
        Owner owner = new Owner(owner_name, password, store_name);
        owner.setProduct_list(owner_prods);
        owner.setOrders(owner_orders);
        return owner;
    }
}