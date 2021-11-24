package com.example.b07project;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Allows customer to select items from a store
 */
public class SelectItems extends AppCompatActivity {
    private Owner storeOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_items);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void setStore(Owner owner) {
        this.storeOwner = owner;
    }
}
