package com.example.b07project;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class CreateAccount extends AppCompatActivity implements Contract.View{
    private Contract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
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

    public void getStore_Name(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateAccount.this);
        builder.setCancelable(true);
        builder.setTitle("Enter Store Name");
        final EditText name = new EditText(CreateAccount.this);
        builder.setView(name);
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.setPositiveButton("Enter", (dialog, which) -> {
            if(!name.getText().toString().equals("")){
                presenter.Create_Owner(name.getText().toString());
            }else{
                Alert("Invalid Store Name", "Store Name Cannot be Blank");
            }
        });
        builder.show();
    }

    public void Alert(String title, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateAccount.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setNegativeButton("Close", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    public void ClickOwner(View view){
        getStore_Name();
    }

    public void ClickCustomer(View view) {
        presenter.Create_Customer();
    }
}