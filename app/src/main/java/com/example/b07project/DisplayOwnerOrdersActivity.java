package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class DisplayOwnerOrdersActivity extends AppCompatActivity {

    Owner owner;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_owner_orders);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if(intent.getExtras() !=null){
            owner = (Owner) intent.getSerializableExtra("Owner");
        }

        if (owner != null){
            listview = findViewById(R.id.listview_ownerorders);
            if (owner.getOrders().isEmpty()){
                ArrayList<String> strings = new ArrayList<>();
                strings.add("No Orders");
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, strings);
                listview.setAdapter(adapter);
            }else{
                /*
                ArrayList<Product> products = new ArrayList<>();
                products.add(new Product("Banana", "Dole", 2.99));
                products.add(new Product("Apple", "Nofrills", 1.99));
                products.add(new Product("Cookies", "Chips Ahoy", 1));

                ArrayList<Order> all_orders = new ArrayList<>();
                all_orders.add(new Order("Aydin", "admin", products, false));


                 */

                ArrayAdapter<Order> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, owner.orders);
                listview.setAdapter(adapter);
            }
        }

    }
}