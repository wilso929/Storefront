package com.example.b07project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class OwnerCompleteOrderActivity extends AppCompatActivity {

    Owner owner;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_complete_order);

        Intent intent = getIntent();
        if(intent.getExtras() !=null) {
            owner = (Owner) intent.getSerializableExtra(DisplayOwnerActivity.Owner_Key);
        }

        if (owner.orders.isEmpty()){
            spinner = (Spinner) findViewById(R.id.SpinnerOrders);

            ArrayList<String> strings = new ArrayList<>();
            strings.add("No Orders");

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, strings);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinner.setAdapter(adapter);

            Button button = (Button)findViewById(R.id.completeorderbutton);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    displayAlert("No order to be completed");
                }
            });
        }else{
            //create a spinner with all owner products
            spinner = (Spinner) findViewById(R.id.SpinnerOrders);

            ArrayList<Order> orders = owner.getOrders();

            ArrayAdapter<Order> adapter = new ArrayAdapter<Order>(this, android.R.layout.simple_spinner_dropdown_item, orders);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinner.setAdapter(adapter);

            Button button = (Button)findViewById(R.id.completeorderbutton);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Order order = (Order) spinner.getSelectedItem();

                    if (order.completed)
                        displayAlert("Order already completed");
                    for (Order o: owner.getOrders()){
                        if (o.equals(order)){
                            o.setCompleted(true);
                        }
                    }

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference();

                    //set the order of owner to completed
                    //set the order of customer to completed
                    myRef.child("Owners").child(owner.getUsername()).child("order_list").child(order.getCustomer()).child("completed").setValue(true);
                    myRef.child("Customers").child(order.getCustomer()).child("order_list").child(owner.getUsername()).child("completed").setValue(true);

                    displayAlert("Order Completed");
                }
            });
        }
    }

    public void displayAlert(String title){
        AlertDialog.Builder builder = new AlertDialog.Builder(OwnerCompleteOrderActivity.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setPositiveButton("Back to Home", (dialog, which) -> {
            sendHome();
        });
        builder.show();
    }

    public void sendHome(){
        Intent intent = new Intent(this, DisplayOwnerActivity.class);
        intent.putExtra(DisplayOwnerActivity.Owner_Key, owner);
        startActivity(intent);
    }

    public void backButton(View view){
        Intent intent = new Intent(this, DisplayOwnerActivity.class);
        intent.putExtra(DisplayOwnerActivity.Owner_Key, this.owner);
        startActivity(intent);
    }

}