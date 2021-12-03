package com.example.b07project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class DeleteOwnerProductActivity extends AppCompatActivity {

    Spinner spinner;
    Owner owner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_owner_product);

        Intent intent = getIntent();
        if(intent.getExtras() !=null) {
            owner = (Owner) intent.getSerializableExtra(DisplayOwnerActivity.Owner_Key);
        }

        if (owner.product_list.isEmpty()){
            spinner = (Spinner) findViewById(R.id.SpinnerOrders);

            ArrayList<String> strings = new ArrayList<>();
            strings.add("No Products");

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, strings);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinner.setAdapter(adapter);

            Button button = (Button)findViewById(R.id.completeorderbutton);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    displayAlert("No Product to be deleted");
                }
            });
        }else{
            //create a spinner with all owner products
            spinner = (Spinner) findViewById(R.id.SpinnerOrders);

            ArrayList<Product> products = owner.getProduct_list();

            ArrayAdapter<Product> adapter = new ArrayAdapter<Product>(this, android.R.layout.simple_spinner_dropdown_item, products);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinner.setAdapter(adapter);

            Button button = (Button)findViewById(R.id.completeorderbutton);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Product p = (Product) spinner.getSelectedItem();
                    owner.getProduct_list().remove(p);

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference();

                    myRef.child("Owners").child(owner.getUsername()).child("product_list").child(p.name).removeValue();

                    displayAlert("Product Deleted");
                }
            });
        }
    }

    public void deleteProduct(Product delete_prod){
        owner.getProduct_list().remove(delete_prod);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("Owners").child(owner.getUsername()).child("product_list").child(delete_prod.name).removeValue();

        //loop through all of the owners orders
        for (Order o: owner.orders){
            for (Product p: o.products){
                if (p.equals(delete_prod)){
                    o.products.remove(p);
                }
            }
        }

        //


        //go through each product in each order in each customer
        myRef.child("Customers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds_customers: snapshot.getChildren()){
                    for (DataSnapshot ds_customerfields: ds_customers.getChildren()){
                        for (DataSnapshot ds_orders: ds_customerfields.child("order_list").getChildren()){
                            for (DataSnapshot ds_products: ds_orders.child("product_list").getChildren()){
                                Product comp = ds_products.getValue(Product.class);
                                if (delete_prod.equals(comp))
                                    ds_products.getRef().removeValue();
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });



    }

    public void displayAlert(String title){
        AlertDialog.Builder builder = new AlertDialog.Builder(DeleteOwnerProductActivity.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setPositiveButton("Back to Home", (dialog, which) -> {
            sendHome();
        });
        builder.show();
    }

    public void sendHome(){
        Intent intent = new Intent(this, DisplayOwnerActivity.class);
        intent.putExtra(DisplayOwnerActivity.Owner_Key, (Serializable) owner);
        startActivity(intent);
    }

    public void backButton(View view){
        Intent intent = new Intent(this, DisplayOwnerActivity.class);
        intent.putExtra(DisplayOwnerActivity.Owner_Key, (Serializable) this.owner);
        startActivity(intent);
    }


    public void Alert(String title, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(DeleteOwnerProductActivity.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setNegativeButton("Close", (dialog, which) -> dialog.cancel());
        builder.show();
    }
}