package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CustomerLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}