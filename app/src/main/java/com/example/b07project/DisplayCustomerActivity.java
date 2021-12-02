package com.example.b07project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;

public class DisplayCustomerActivity extends AppCompatActivity {
    private Customer customer;
    public static String Customer_Key = "Customer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle intentExtras = this.getIntent().getExtras();
        if (intentExtras != null) {
            this.customer = (Customer) intentExtras.getParcelable(Customer_Key);
        }
        setContentView(R.layout.activity_customer_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // user story 7, 8
    public void makeNewOrder(View view) {
        Intent intent = new Intent(this, SelectStore.class);
        intent.putExtra(Customer_Key, (Parcelable) this.customer);
        startActivity(intent);
    }

    public void view_orders(View view){
        Intent intent = new Intent(this, DisplayCustomerOrdersActivity.class);
        intent.putExtra(Customer_Key, (Parcelable) this.customer);
        startActivity(intent);
    }

    public void seeAvailableStores(View view) {
        Intent intent = new Intent(this, SelectStore.class);
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

    public void backButton(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}