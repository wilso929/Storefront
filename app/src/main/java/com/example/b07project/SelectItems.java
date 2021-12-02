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

            Order order = new Order(customer.getUsername(), orderItems, false);
            customer.add_order(order); // update the customer's order list
            selectedOwner.addOrder(order); // update the store owner's order list
            this.updateFirebase(true, this.customer, this.selectedOwner,
                    order); // updates Owner
            this.updateFirebase(false, this.customer, this.selectedOwner,
                    order); // updates Customer
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
     * @param userTypeOwner Enter true if you want to update the owner, false if you want to update
     *                      the customer
     * @param customer The customer that made the order
     * @param owner The owner that the customer made the order to
     * @param order the order that the customer made
     */
    private void updateFirebase(final boolean userTypeOwner, Customer customer, Owner owner,
                                Order order) {
        if (customer != null && owner != null && order != null) {
            final String userType = userTypeOwner ? "Owners" : "Customers";
            final String orderListUserName = userTypeOwner ? customer.getUsername() :
                    owner.getUsername();
            final String primaryUserName = userTypeOwner ? owner.getUsername() :
                    customer.getUsername();
            ArrayList<Product> products = order.getProducts();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference().child(userType).child(
                    primaryUserName).child("order_list").child(orderListUserName);
            myRef.child("Customer").setValue(order.getCustomer());
            myRef.child("Completed").setValue(order.isCompleted());

            for (Product product : products) {
                myRef.child("product_list").child(product.getName()).setValue(product);
            }
        }
    }
}
