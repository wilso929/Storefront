package com.example.b07project;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyModel implements Contract.Model{

    public MyModel(){
    }

    public void Check_Info(String username, String password, String type, Contract.Presenter presenter){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(type);
        ref.child(username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("demo", "Error getting data", task.getException());
                }else {
                    if(task.getResult().exists()){
                        String check = "";
                        for(DataSnapshot snapshot : task.getResult().getChildren()) {
                            if (snapshot.getKey().equals("password")) {
                                check = snapshot.getValue().toString();
                            }
                        }
                        if(password.equals(check)){
                            GenerateUser(username, type, presenter);
                        }else{
                            presenter.Invaildlogin();
                        }
                    }else{
                        presenter.Invaildlogin();
                    }
                }
            }
        });
    }

    public void Add_User(User user, String type, Contract.Presenter presenter){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef  = database.getReference();

        myRef.child(type).addValueEventListener(new ValueEventListener()  {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(user.getUsername()).exists()) {
                    presenter.Create_Failed();
                }else{
                    myRef.child(type).child(user.getUsername()).setValue(user);
                    GenerateUser(user.getUsername(), type, presenter);
                }
                myRef.child(type).removeEventListener(this);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Tag","Error while reading data");
            }
        });
    }


    public static void GenerateUser(String username, String type, Contract.Presenter presenter){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(type);
        ref.child(username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("demo", "Error getting data", task.getException());
                }
                else {
                    if(type.equals("Owners")){
                        Owner owner = new Owner();
                        owner.setUsername(username);
                        for(DataSnapshot snapshot : task.getResult().getChildren()){
                            if(snapshot.getKey().equals("password")){
                                owner.setPassword(snapshot.getValue().toString());
                            }else if(snapshot.getKey().equals("product_list")){
                                ArrayList<Product> product_list = new ArrayList<Product>();
                                for(DataSnapshot ds : snapshot.getChildren()){
                                    Product product = ds.getValue(Product.class);
                                    product_list.add(product);
                                }
                                owner.setProduct_list(product_list);
                            }else if(snapshot.getKey().equals("order_list")){
                                ArrayList<Order> order_list = new ArrayList<Order>();
                                for(DataSnapshot ds : snapshot.getChildren()){
                                    Order order = new Order();
                                    for(DataSnapshot snap : ds.getChildren()){
                                        if(snap.getKey().equals("customer")){
                                            order.setCustomer(snap.getValue().toString());
                                        }else if(snap.getKey().equals("owner")){
                                            order.setOwner(snap.getValue().toString());
                                        }else if(snap.getKey().equals("completed")){
                                            order.setCompleted(Boolean.parseBoolean(snap.getValue().toString()));
                                        }else if(snap.getKey().equals("product_list")){
                                            ArrayList<Product> product_list = new ArrayList<Product>();
                                            for(DataSnapshot p : snap.getChildren()){
                                                Product product = p.getValue(Product.class);
                                                product_list.add(product);
                                            }
                                            order.setProducts(product_list);
                                        }
                                    }
                                    order_list.add(order);
                                }
                                owner.setOrders(order_list);
                            }else if(snapshot.getKey().equals("store_name")){
                                owner.setStore_name(snapshot.getValue().toString());
                            }
                        }
                        presenter.Vaildlogin(owner, type);
                    }else{
                        Customer customer = new Customer();
                        customer.setUsername(username);
                        for(DataSnapshot snapshot : task.getResult().getChildren()){
                            if(snapshot.getKey().equals("password")){
                                customer.setPassword(snapshot.getValue().toString());
                            }else if(snapshot.getKey().equals("order_list")){
                                ArrayList<Order> order_list = new ArrayList<Order>();
                                for(DataSnapshot ds : snapshot.getChildren()){
                                    Order order = new Order();
                                    for(DataSnapshot snap : ds.getChildren()){
                                        if(snap.getKey().equals("customer")){
                                            order.setCustomer(snap.getValue().toString());
                                        }else if(snap.getKey().equals("owner")){
                                            order.setOwner(snap.getValue().toString());
                                        }else if(snap.getKey().equals("completed")){
                                            order.setCompleted(Boolean.parseBoolean(snap.getValue().toString()));
                                        }else if(snap.getKey().equals("product_list")){
                                            ArrayList<Product> product_list = new ArrayList<Product>();
                                            for(DataSnapshot p : snap.getChildren()){
                                                Product product = p.getValue(Product.class);
                                                product_list.add(product);
                                            }
                                            order.setProducts(product_list);
                                        }
                                    }
                                    order_list.add(order);
                                }
                                customer.setOrders(order_list);
                            }
                        }
                        presenter.Vaildlogin(customer, type);
                    }
                }
            }
        });
    }
}

