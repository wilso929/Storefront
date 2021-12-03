package com.example.b07project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public class DisplayOwnerActivity extends AppCompatActivity {

    Owner owner;
    public static String Owner_Key = "b07Project";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_owner);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if(intent.getExtras() !=null) {
            this.owner = (Owner) intent.getSerializableExtra(Owner_Key);
        }

    }

    public void view_products(View view) {
        Intent intent = new Intent(this, DisplayOwnerProductsActivity.class);
        intent.putExtra(Owner_Key, (Serializable) this.owner);
        startActivity(intent);
    }

    public void view_orders(View view){
        Intent intent = new Intent(this, DisplayOwnerOrdersActivity.class);
        intent.putExtra(Owner_Key, (Serializable) this.owner);
        startActivity(intent);
    }

    public void add_product(View view){
        Intent intent = new Intent(this, AddOwnerProductActivity.class);
        intent.putExtra(Owner_Key, (Serializable) this.owner);
        startActivity(intent);
    }

    public void delete_product(View view){
        Intent intent = new Intent(this, DeleteOwnerProductActivity.class);
        intent.putExtra(Owner_Key, (Serializable) this.owner);
        startActivity(intent);
    }

    public void complete_order(View view){
        Intent intent = new Intent(this, OwnerCompleteOrderActivity.class);
        intent.putExtra(Owner_Key, (Serializable) this.owner);
        startActivity(intent);
    }

    public void sendHome(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void delete_product(View view){
        Intent intent = new Intent(this, DeleteOwnerProductActivity.class);
        intent.putExtra(Owner_Key, this.owner);
        startActivity(intent);
    }

    public void complete_order(View view){
        Intent intent = new Intent(this, OwnerCompleteOrderActivity.class);
        intent.putExtra(Owner_Key, this.owner);
        startActivity(intent);
    }

    public void sendHome(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void Alert(String title, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(DisplayOwnerActivity.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setNegativeButton("Close", (dialog, which) -> dialog.cancel());
        builder.show();
    }



}