package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

public class DisplayCustomerOrdersActivity extends AppCompatActivity {

    Customer customer;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_customer_orders);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if(intent.getExtras() !=null){
            customer = (Customer) intent.getParcelableExtra(DisplayCustomerActivity.Customer_Key);
        }

        if (customer != null){
            listview = findViewById(R.id.listview_customerorders);
            if (customer.getOrders().isEmpty()){
                ArrayList<String> strings = new ArrayList<>();
                strings.add("No Orders");
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, strings);
                listview.setAdapter(adapter);
            }else{
                ArrayAdapter<Order> adapter = new ArrayAdapter<Order>(this, android.R.layout.simple_list_item_1, customer.orders);
                listview.setAdapter(adapter);
            }
        }
    }

    public void backButton(View view){
        Intent intent = new Intent(this, DisplayCustomerActivity.class);
        intent.putExtra(DisplayCustomerActivity.Customer_Key, (Parcelable) this.customer);
        startActivity(intent);
    }

}