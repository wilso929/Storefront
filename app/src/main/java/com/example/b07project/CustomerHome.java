package com.example.b07project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;

/**
 * Main Menu for customers
 */
public class CustomerHome extends AppCompatActivity {
    private HashSet<Owner> allOwners;
    private Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle intentExtras = this.getIntent().getExtras();
        this.allOwners = (intentExtras == null) ? null :
                (HashSet<Owner>) intentExtras.getSerializable("All Owners");
        this.customer = (intentExtras == null) ? null :
                (Customer) intentExtras.getParcelable("Customer");
        setContentView(R.layout.activity_customer_home);
    }

    // user story 7, 8
    public void makeNewOrder(View view) {
        Intent intent = new Intent(this, SelectStore.class);
        intent.putExtra("All Owners", this.allOwners);
        intent.putExtra("Customer", this.customer);
        startActivity(intent);
    }

    // user story 6
    public void seeAvailableStores(View view) {

    }
}
