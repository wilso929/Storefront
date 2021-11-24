package com.example.b07project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Owner_Login(View view){
        Intent intent = new Intent(this, OwnerLogin.class);
        startActivity(intent);

    }

    public void Customer_Login(View view){
        Intent intent = new Intent(this, CustomerLogin.class);
        startActivity(intent);

    }

    public void Create_Account(View view){
        Intent intent = new Intent(this, CreateAccount.class);
        startActivity(intent);
    }

    public void Select_Store(View view) {
        Intent intent = new Intent(this, SelectStore.class);
        startActivity(intent);
    }
}