package com.example.b07project;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateAccount extends AppCompatActivity {
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void Create_Owner(View view){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        EditText text = findViewById(R.id.Username);
        username = text.getText().toString();
        text = findViewById(R.id.Password);
        password = text.getText().toString();
        DatabaseReference myRef  = database.getReference();

        myRef.child("Owners").addValueEventListener(new ValueEventListener()  {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(username).exists()) {
                    Alert("Username Invalid", "Username already exists.");
                }else if(Authenticate()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateAccount.this);
                    builder.setCancelable(true);
                    builder.setTitle("Enter Store Name");
                    final EditText name = new EditText(CreateAccount.this);
                    builder.setView(name);
                    builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
                    builder.setPositiveButton("Enter", (dialog, which) -> {
                        if(!name.getText().toString().equals("")){
                            Owner owner = new Owner(username, password, name.getText().toString());
                            myRef.child("Owners").child(username).setValue(owner);
                        }else{
                            Alert("Invalid Store Name", "Store Name Cannot be Blank");
                        }
                    });
                    builder.show();
                }
                myRef.child("Owners").removeEventListener(this);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Tag","Error while reading data");
            }
        });
    }

    public void Create_Customer(View view){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        EditText text = findViewById(R.id.Username);
        username = text.getText().toString();
        text = findViewById(R.id.Password);
        password = text.getText().toString();

        DatabaseReference myRef  = database.getReference();

        myRef.child("Customers").addValueEventListener(new ValueEventListener()  {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(username).exists()) {
                    Alert("Username Invalid", "Username already exists.");
                }else if(Authenticate()){

                    Customer customer = new Customer(username, password);
                    myRef.child("Customers").child(username).setValue(customer);
                }
                myRef.child("Customers").removeEventListener(this);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Tag","Error while reading data");
            }
        });
    }

    public void Alert(String title, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateAccount.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setNegativeButton("Close", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    public boolean Authenticate(){
        Pattern name = Pattern.compile("[a-zA-Z0-9]{7,}");
        Pattern pass = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{7,}$");
        Matcher matchn = name.matcher(username);
        Matcher matchp = pass.matcher(password);
        if(!matchn.matches()){
            Alert("Invalid Username", "Usernames must be at least 7 characters long");
            return false;
        }else if(!matchp.matches()) {
            Alert("Invalid Password", "Must be: " +
                    "at least 7 characters, " +
                    "include a lowercase, " +
                    "an uppercase, a number, " +
                    "a special character, " +
                    "and not other character types.");
            return false;
        }
        return true;

    }



}