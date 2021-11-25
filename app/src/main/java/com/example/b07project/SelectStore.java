package com.example.b07project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;

/**
 * Class allows customers to select a store to make an order
 */
public class SelectStore extends AppCompatActivity {
    private HashSet<Owner> allOwners;
    private Customer customer;
    private HashSet<RadioButton> allRadioButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the allOwners HashSet and customer
        Bundle intentExtras = getIntent().getExtras(); // a HashSet of owners
        this.allOwners = (intentExtras == null) ? new HashSet<>() :
                (HashSet<Owner>) intentExtras.getSerializable("All Owners");
        this.customer = (intentExtras == null) ? null :
                (Customer) intentExtras.getParcelable("Customer");
        this.allRadioButtons = new HashSet<RadioButton>();

        setContentView(R.layout.activity_select_store);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        displayAllStores();
    }

    /**
     * Displays all the stores
     */
    private void displayAllStores() {
        RadioButton button = null;
        RadioGroup rg = (RadioGroup) findViewById(R.id.RadioGroup1);
        int i = 10546079;

        for (Owner owner : allOwners) {
            button = new RadioButton(this);
            button.setText(owner.getStore_name());
            button.setId(i);
            allRadioButtons.add(button);
            rg.addView(button);
            i++;
        }
    }

    /**
     * Once the OK button is selected, this method directs user
     * to make an order from the store they selected.
     * @param view
     */
    public void onStoreSelected(View view) {
        Object[] ownersArray = allOwners.toArray();
        Owner selectedOwner = null;

        int i = 0;
        for (RadioButton rb : allRadioButtons) {
            if (rb.isChecked()) {
                selectedOwner = (Owner) ownersArray[i];
            }
            i++;
        }

        if (selectedOwner != null) {
            Intent intent = new Intent(this, SelectItems.class);
            intent.putExtra("Selected Owner", selectedOwner);
            intent.putExtra("Customer", this.customer);
            intent.putExtra("All Owners", this.allOwners);
            startActivity(intent);
        }
    }

    public void goBack(View view) {
        Intent intent = new Intent(this, CustomerHome.class);
        intent.putExtra("Customer", this.customer);
        intent.putExtra("All Owners", this.allOwners);
        startActivity(intent);
    }
}
