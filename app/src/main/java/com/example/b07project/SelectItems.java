package com.example.b07project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashSet;

/**
 * Allows customer to select items from a store to make an order
 */
public class SelectItems extends AppCompatActivity {
    private Owner selectedOwner;
    private Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_items);
        // Lookup the recyclerview in activity layout
        RecyclerView rv = (RecyclerView) findViewById(R.id.storeInventoryRV);

        // Initialize selectedOwner and customer
        Bundle intentExtras = getIntent().getExtras();
        this.selectedOwner = (intentExtras == null) ? null :
                (Owner) intentExtras.getParcelable("Selected Owner");
        this.customer = (intentExtras == null) ? null :
                (Customer) intentExtras.getParcelable("Customer");

        // Create adapter and set it as the adapter for the recyclerview
        SelectItemsAdapter adapter = new
                SelectItemsAdapter(this.selectedOwner);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * once the customer selects items and adjust the quantities
     * of each item and presses OK button,
     * this method will process the order and update
     * the customer and owner. It will return the customer back to
     * the main menu.
     * @param view
     */
    public void makeOrder(View view) {

        goBack(view); // return customer back to the main menu
    }

    /**
     * Returns customer back to the main menu when the customer
     * clicks CANCEL or OK button.
     * @param view
     */
    public void goBack(View view) {
        Intent intent = new Intent(this, CustomerHome.class);
        Bundle intentExtras = this.getIntent().getExtras();
        HashSet<Owner> allOwners = (intentExtras == null) ? null :
                (HashSet<Owner>) intentExtras.getSerializable("All Owners");

        intent.putExtra("Customer", this.customer);
        intent.putExtra("All Owners", allOwners);
        startActivity(intent);
    }
}
