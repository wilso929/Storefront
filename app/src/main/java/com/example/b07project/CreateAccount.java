package com.example.b07project;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void Create_Owner(View view){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        EditText text = findViewById(R.id.Username);
        String username = text.getText().toString();
        text = findViewById(R.id.Password);
        String password = text.getText().toString();

        DatabaseReference myRef = database.getReference("Owners/"+username+"/Username");
        myRef.setValue(username);
        myRef = database.getReference("Owners/"+username+"/Password");
        myRef.setValue(password);
    }

    public void Create_Customer(View view){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        EditText text = findViewById(R.id.Username);
        String username = text.getText().toString();
        text = findViewById(R.id.Password);
        String password = text.getText().toString();
        DatabaseReference myRef = database.getReference("Customers/"+username+"/Username");
        myRef.setValue(username);
        myRef = database.getReference("Customers/"+username+"/Password");
        myRef.setValue(password);
    }

}