package com.example.b07project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;

public class DisplayCustomerActivity extends AppCompatActivity {
    public ArrayList<Owner> allOwners;
    private Customer customer;
    private Contract.Other other = new MyOther(new MyModel());

    public static String Customer_Key = "Customer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle intentExtras = this.getIntent().getExtras();
        if (intentExtras != null) {
            this.customer = (Customer) intentExtras.getParcelable(Customer_Key);
        }

        this.allOwners = new ArrayList<Owner>();
        other.Update_Owners(this);
        setContentView(R.layout.activity_customer_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // user story 7, 8
    public void makeNewOrder(View view) {
        Intent intent = new Intent(this, SelectStore.class);
        intent.putExtra("All Owners", this.allOwners);
        intent.putExtra(Customer_Key, (Parcelable) this.customer);
        startActivity(intent);
    }

    public void seeAvailableStores(View view) {
        Intent intent = new Intent(this, SelectStore.class);
        intent.putExtra("All Owners", this.allOwners);
        intent.putExtra(Customer_Key, (Parcelable) this.customer);
        startActivity(intent);
    }

    public void Alert(String title, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(DisplayCustomerActivity.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setNegativeButton("Close", (dialog, which) -> dialog.cancel());
        builder.show();
    }



}