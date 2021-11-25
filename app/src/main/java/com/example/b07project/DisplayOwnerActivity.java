package com.example.b07project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DisplayOwnerActivity extends AppCompatActivity {

    Owner owner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_owner);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if(intent.getExtras() !=null){
            this.owner = (Owner) intent.getSerializableExtra("User");
        }
        Product pro = new Product("Tide", "Tide", 0.99);
        owner.add_product(pro);
        for(Product p : owner.getProduct_list()){
            Log.i("pro", p.getName());
        }
        Alert("Pass", owner.getPassword());


    }

    public void view_products(View view) {
        Intent intent = new Intent(this, DisplayOwnerProductsActivity.class);
        intent.putExtra("Owner", this.owner);
        startActivity(intent);
    }

    public void view_orders(View view){}

    public void Alert(String title, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(DisplayOwnerActivity.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setNegativeButton("Close", (dialog, which) -> dialog.cancel());
        builder.show();
    }



}