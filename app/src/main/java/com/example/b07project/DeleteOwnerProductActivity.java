package com.example.b07project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DeleteOwnerProductActivity extends AppCompatActivity {

    Spinner spinner;
    Owner owner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_owner_product);

        Intent intent = getIntent();
        if(intent.getExtras() !=null) {
            owner = (Owner) intent.getSerializableExtra(DisplayOwnerActivity.Owner_Key);
        }


        //create a spinner with all owner products
        spinner = (Spinner) findViewById(R.id.SpinnerProducts);

        ArrayList<Product> products = owner.getProduct_list();

        ArrayAdapter<Product> adapter = new ArrayAdapter<Product>(this, android.R.layout.simple_spinner_dropdown_item, products);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        Button button = (Button)findViewById(R.id.deleteproductbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product p = (Product) spinner.getSelectedItem();
                owner.getProduct_list().remove(p);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();

                myRef.child("Owners").child(owner.getUsername()).child("product_list").child(p.name).removeValue();

                sendHome();
            }
        });


    }

    public void sendHome(){
        Intent intent = new Intent(this, DisplayOwnerActivity.class);
        intent.putExtra(DisplayOwnerActivity.Owner_Key, owner);
        startActivity(intent);
    }


    public void Alert(String title, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(DeleteOwnerProductActivity.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setNegativeButton("Close", (dialog, which) -> dialog.cancel());
        builder.show();
    }
}