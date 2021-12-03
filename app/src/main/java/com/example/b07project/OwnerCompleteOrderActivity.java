package com.example.b07project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class OwnerCompleteOrderActivity extends AppCompatActivity {

    Owner owner;
    Order final_order;
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

            TextView tv = findViewById(R.id.textView_displayorder);
            tv.setText("No Orders");

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

            //ArrayAdapter<Order> adapter = new ArrayAdapter<Order>(this, android.R.layout.simple_spinner_dropdown_item, orders);
            //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            ArrayList<String> order_strings = new ArrayList<>();
            for (Order o: orders){
                String s = o.customer + " | ";
                if (o.completed)
                    s += "Completed | ";
                else
                    s += "Not Completed | ";
                int order_num_items = o.products.size();
                s += order_num_items + " items";
                order_strings.add(s);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, order_strings);
            adapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);

            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    //Order order = (Order) spinner.getSelectedItem();
                    Order order = orders.get(position);
                    final_order = order;
                    TextView tv = findViewById(R.id.textView_displayorder);
                    tv.setText(order.toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            Button button = (Button)findViewById(R.id.completeorderbutton);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (spinner.getSelectedItem().equals("No Orders")){
                        displayAlert("No order to be completed");
                        return;
                    }

                    else if (final_order.completed){
                        displayAlert("Order already completed");
                        return;
                    }
                    else{
                        for (Order o: owner.getOrders()){
                            if (o.equals(final_order)){
                                o.setCompleted(true);
                            }
                        }

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference();

                        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (!task.isSuccessful()) {
                                    Log.e("demo", "Error getting data", task.getException());

                                } else {
                                    for (DataSnapshot big_snapshot : task.getResult().getChildren()) {
                                        if (big_snapshot.getKey().equals("Customers")) {
                                            for (DataSnapshot snapshot : big_snapshot.getChildren()) {
                                                String customer_key = snapshot.getKey();
                                                for (DataSnapshot small_snapshot : snapshot.getChildren()) {
                                                    if (small_snapshot.getKey().equals("order_list")) {
                                                        for (DataSnapshot tiny_snapshot : small_snapshot.getChildren()) {
                                                            if (tiny_snapshot.getKey().contains(owner.store_name) && tiny_snapshot.getKey().contains(final_order.getCustomer())) {
                                                                String order_key = tiny_snapshot.getKey();
                                                                myRef.child("Customers").child(customer_key)
                                                                        .child("order_list").child(order_key)
                                                                        .child("completed").setValue("true");
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        } else if (big_snapshot.getKey().equals("Owners")) {
                                            for (DataSnapshot snapshot : big_snapshot.getChildren()) {
                                                if (snapshot.getKey().equals(owner.getUsername())) {
                                                    for (DataSnapshot small_snapshot : snapshot.getChildren()) {
                                                        if (small_snapshot.getKey().equals("order_list")) {
                                                            for (DataSnapshot tiny_snapshot : small_snapshot.getChildren()) {
                                                                String order_key = tiny_snapshot.getKey();
                                                                myRef.child("Owners").child(owner.getUsername())
                                                                        .child("order_list").child(order_key)
                                                                        .child("completed").setValue("true");
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        });
                        displayAlert("Order Completed");
                    }
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
        intent.putExtra(DisplayOwnerActivity.Owner_Key, (Serializable) owner);
        startActivity(intent);
    }

    public void backButton(View view){
        Intent intent = new Intent(this, DisplayOwnerActivity.class);
        intent.putExtra(DisplayOwnerActivity.Owner_Key, (Serializable) this.owner);
        startActivity(intent);
    }

}