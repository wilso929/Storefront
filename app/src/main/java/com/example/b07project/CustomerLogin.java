package com.example.b07project;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class CustomerLogin extends AppCompatActivity implements Contract.View {

    private Contract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_login);
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

}
