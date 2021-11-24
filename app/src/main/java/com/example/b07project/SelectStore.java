package com.example.b07project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashSet;

/**
 * Class allows customers to select a store to make an order
 */
public class SelectStore extends AppCompatActivity {
    private HashSet<Owner> allOwners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_store);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        displayAllOwners();
    }

    /**
     * @return a HashSet<Owner> object with all the Owners in Firebase
     */
    private HashSet<Owner> getAllOwners() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef  = database.getReference();
        HashSet<Owner> result = new HashSet<Owner>();
        return result;
    }

    /**
     * Displays all the owners
     */
    private void displayAllOwners() {
        RadioButton button = null;
        RadioGroup rg = (RadioGroup) findViewById(R.id.RadioGroup1);
        int i = 10546079;

        this.allOwners = this.getAllOwners();

        for (Owner owner : allOwners) {
            button = new RadioButton(this);
            button.setText(owner.getStore_name());
            button.setId(i);
            rg.addView(button);
            i++;
        }
    }

    /**
     * Once the OK button is selected, direct user to select the store's items
     * @param view
     */
    public void onStoreSelected(View view) {

    }
}
