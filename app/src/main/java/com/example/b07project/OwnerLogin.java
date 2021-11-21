package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class OwnerLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}