package com.example.b07project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

}