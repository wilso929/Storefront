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
    }

    public void view_products(View view) {
        Intent intent = new Intent(this, DisplayOwnerProductsActivity.class);
        intent.putExtra("Owner", this.owner);
        startActivity(intent);
    }

    public void view_orders(View view){}

}