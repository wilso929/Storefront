package com.example.b07project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddOwnerProductActivity extends AppCompatActivity {

    Owner owner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_owner_product);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if(intent.getExtras() !=null) {
            owner = (Owner) intent.getSerializableExtra(DisplayOwnerActivity.Owner_Key);
        }

    }

    public void onClick(View view){
        String name = getProdName();
        String brand_name = getBrandName();
        double price = getProdPrice();
        Product new_prod = new Product(name, brand_name, price);

        if (owner != null){
            if (owner.productExists(new_prod)){
                //create alert
                Alert("Error", "Product already exists.");
            }else{
                //add product to owners product list and database

                owner.getProduct_list().add(new_prod);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();

                myRef.child("Owners").child(owner.getUsername()).child("product_list").child(name).setValue(new_prod);


                Intent intent = new Intent(this, DisplayOwnerActivity.class);
                intent.putExtra(DisplayOwnerActivity.Owner_Key, owner);
                startActivity(intent);


            }
        }

    }


    public String getProdName(){
        EditText text = findViewById(R.id.ProductName);
        return text.getText().toString();
    }

    public String getBrandName(){
        EditText text = findViewById(R.id.BrandName);
        return text.getText().toString();
    }

    public double getProdPrice(){
        EditText text = findViewById(R.id.ProductPrice);
        String s = text.getText().toString();
        return Double.parseDouble(s);
    }

    public void Alert(String title, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(AddOwnerProductActivity.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setNegativeButton("Close", (dialog, which) -> dialog.cancel());
        builder.show();
    }
}