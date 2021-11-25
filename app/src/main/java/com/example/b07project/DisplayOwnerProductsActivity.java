package com.example.b07project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayOwnerProductsActivity extends AppCompatActivity {

    ListView listview;
    Owner owner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_owner_products);

        Intent intent = getIntent();
        if(intent.getExtras() !=null){
            owner = (Owner) intent.getSerializableExtra("Owner");
        }

        listview = findViewById(R.id.listview_products);

        ArrayAdapter<Product> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, owner.product_list);
        listview.setAdapter(adapter);
    }

}