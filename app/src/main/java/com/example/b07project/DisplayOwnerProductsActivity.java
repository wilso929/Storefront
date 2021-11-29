package com.example.b07project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DisplayOwnerProductsActivity extends AppCompatActivity {

    ListView listview;
    Owner owner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_owner_products);

        Intent intent = getIntent();
        if(intent.getExtras() !=null){
            owner = (Owner) intent.getSerializableExtra(DisplayOwnerActivity.Owner_Key);
        }

        if (owner != null) {
            listview = findViewById(R.id.listview_ownerproducts);

            if (owner.getProduct_list().isEmpty()) {
                ArrayList<String> strings = new ArrayList<>();
                strings.add("No Products");
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, strings);
                listview.setAdapter(adapter);
            } else {
                ArrayAdapter<Product> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, owner.product_list);
                listview.setAdapter(adapter);
            }
        }
    }

    public void backButton(View view){
        Intent intent = new Intent(this, DisplayOwnerActivity.class);
        intent.putExtra(DisplayOwnerActivity.Owner_Key, this.owner);
        startActivity(intent);
    }

}