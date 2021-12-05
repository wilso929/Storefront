package com.example.b07project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Class allows customers to select a store to make an order
 */
public class SelectStore extends AppCompatActivity {
    public ArrayList<Owner> allOwners;
    private Customer customer;
    private final Contract.Other other = new MyOther(new MyModel());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the allOwners HashSet and customer
        Bundle intentExtras = getIntent().getExtras();
        if (intentExtras != null) {
            this.customer = (Customer) intentExtras.getParcelable(DisplayCustomerActivity.Customer_Key);
        }
        this.allOwners = new ArrayList<>();
        this.other.Update_Owners(this);

        if (allOwners.isEmpty())
            setContentView(R.layout.activity_no_store);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    /**
     * Displays all the stores
     */
    public void displayAllStores() {
        setContentView(R.layout.activity_select_store);
        RadioButton button;
        RadioGroup rg = (RadioGroup) findViewById(R.id.RadioGroup1);
        int i = 10546079;

        if (allOwners == null || allOwners.isEmpty()) {
            setContentView(R.layout.activity_no_store);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        } else {
            for (Owner owner : allOwners) {
                button = new RadioButton(this);
                button.setText(owner.getStore_name());
                button.setId(i);
                rg.addView(button);
                // i++;
            }
        }
    }

    /**
     * Once the OK button is selected, this method directs user
     * to make an order from the store they selected.
     * @param view the view
     */
    public void onStoreSelected(View view) {
        Object[] ownersArray = allOwners.toArray();
        Owner selectedOwner = null;
        RadioGroup rg = (RadioGroup) findViewById(R.id.RadioGroup1);
        View radioButton;

        for (int i = 0; i < ownersArray.length; i++) {
            radioButton = rg.getChildAt(i);
            if (radioButton instanceof RadioButton) {
                if (((RadioButton) radioButton).isChecked()) {
                    selectedOwner = (Owner) ownersArray[i];
                }
            }
        }

        if (selectedOwner != null) {
            Intent intent = new Intent(this, SelectItems.class);
            intent.putExtra("Selected Owner", (Parcelable) selectedOwner);
            intent.putExtra("Customer", (Parcelable) this.customer);
            intent.putParcelableArrayListExtra("All Owners", this.allOwners);
            startActivity(intent);
        }
    }

    public void goBack(View view) {
        Intent intent = new Intent(this, DisplayCustomerActivity.class);
        intent.putExtra("Customer", (Parcelable) this.customer);
        startActivity(intent);
    }
}
