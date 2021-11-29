package com.example.b07project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;

public class CustomerLogin extends AppCompatActivity implements Contract.View {

    private Contract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new MyPresenter(new MyModel(), this);
    }

    public String getUsername(){
        EditText text = findViewById(R.id.Username);
        return text.getText().toString();
    }
    public String getPassword(){
        EditText text = findViewById(R.id.Password);
        return text.getText().toString();
    }

    public void handleClick(View view){
        presenter.Check_Account("Customers");
    }

    public void Alert(String title, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(CustomerLogin.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setNegativeButton("Close", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    /*public void NextPage(User user, String type){
        Alert("Yay", "You did it");
    }*/

    public void NextPage(User user, String type){
        Intent intent = new Intent(this, DisplayCustomerActivity.class);
        intent.putExtra(DisplayCustomerActivity.Customer_Key, (Parcelable) user);
        startActivity(intent);
    }

}
