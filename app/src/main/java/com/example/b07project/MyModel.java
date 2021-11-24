package com.example.b07project;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyModel implements Contract.Model{

    public MyModel(){
    }

    public void Check_Info(String username, String password, String type, Contract.Presenter presenter){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(type);
        ref.child(username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("demo", "Error getting data", task.getException());
                }else {
                    if(task.getResult().exists()){
                        Owner owner = task.getResult().getValue(Owner.class);
                        if(password.equals(owner.getPassword())) {
                            presenter.Vaildlogin(username);

                        }else{
                            presenter.Invaildlogin();
                        }
                    }else{
                        presenter.Invaildlogin();
                    }
                }
            }
        });
    }
}

