package com.example.b07project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Allows customer to select items from a store to make an order
 */
public class SelectItems extends AppCompatActivity {
    private Owner selectedOwner;
    private Customer customer;
    private SelectItemsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_items);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        // Lookup the recyclerview in activity layout
        RecyclerView recyclerView =
                (RecyclerView) findViewById(R.id.storeInventoryRV);

        // Initialize selectedOwner and customer
        Bundle intentExtras = getIntent().getExtras();
        this.selectedOwner = (intentExtras == null) ? null :
                (Owner) intentExtras.getParcelable("Selected Owner");
        this.customer = (intentExtras == null) ? null :
                (Customer) intentExtras.getParcelable("Customer");

        // Create adapter and set it as the adapter for the recyclerview
        this.adapter = new SelectItemsAdapter(this.selectedOwner);
        recyclerView.setAdapter(this.adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * once the customer selects items and adjust the quantities
     * of each item and presses OK button,
     * this method will process the order and update
     * the customer and owner. It will return the customer back to
     * the main menu.
     * @param view the view
     */
    public void makeOrder(View view) {
        int currentQuantity;
        int i = 0; // index variable
        ArrayList<Product> orderItems = new ArrayList<>();

        if (this.selectedOwner != null &&
                this.selectedOwner.product_list != null &&
                this.adapter != null) {
            for (Product product : this.selectedOwner.product_list) {
                currentQuantity = this.adapter.getQuantityAtPosition(i);

                // add the product into orderItems currentQuantity number of times
                for (int j = 0; j < currentQuantity; j++) {
                    orderItems.add(new Product(product.getName(), product.getBrand(),
                            product.getPrice()));
                }

                i++;
            }

            Date date = Calendar.getInstance(TimeZone.getTimeZone("America/Toronto")).getTime();
            Order order = new Order(customer.getUsername(), selectedOwner.getUsername(),
                    orderItems, false);
            customer.add_order(order); // update the customer's order list
            selectedOwner.addOrder(order); // update the store owner's order list
            this.updateFirebase(true, order, date); // update Owner
            this.updateFirebase(false, order, date); // update Customer
        }
        goBack(view); // return customer back to the main menu
    }

    /**
     * Returns customer back to the main menu when the customer
     * clicks CANCEL or OK button.
     * @param view the view
     */
    public void goBack(View view) {
        Intent intent = new Intent(this, DisplayCustomerActivity.class);
        Bundle intentExtras = this.getIntent().getExtras();
        ArrayList<Owner> allOwners = (intentExtras == null) ? null :
                intentExtras.getParcelableArrayList("All Owners");

        intent.putExtra("Customer", (Parcelable) this.customer);
        intent.putParcelableArrayListExtra("All Owners", allOwners);
        startActivity(intent);
    }

    /**
     * A method that updates a user's firebase data once an order is made
     *
     * @param updateOwner The value of this is true if you want to update the owner,
     *                    false if you want to update the customer.
     * @param order the order that the customer made
     * @param date the date and time that the order was made
     */
    private void updateFirebase(final boolean updateOwner, Order order, Date date) {
        if (adapter != null && customer != null && selectedOwner != null && order != null &&
                date != null && selectedOwner.getProduct_list() != null) {
            final String userType = updateOwner ? "Owners" : "Customers";
            final String primaryUserName = updateOwner ? selectedOwner.getUsername() :
                    customer.getUsername();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss",
                    Locale.CANADA);
            final String orderName = selectedOwner.getStore_name() + " | "  +
                    dateFormat.format(date) + " | From: " + customer.getUsername();
            ArrayList<Product> products = selectedOwner.getProduct_list();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference().child(userType).child(
                    primaryUserName).child("order_list").child(orderName);
            myRef.child("customer").setValue(customer.getUsername());
            myRef.child("owner").setValue(selectedOwner.getUsername());
            myRef.child("completed").setValue(order.isCompleted());

            for (int i = 0; i < products.size(); i++) {
                if (this.adapter.getQuantityAtPosition(i) != 0) {
                    myRef.child("product_list").child(products.get(i).getName()).setValue(
                            products.get(i));
                    myRef.child("product_list").child(products.get(i).getName()).child(
                            "quantity").setValue(this.adapter.getQuantityAtPosition(i));
                }
            }
        }
    }
}
