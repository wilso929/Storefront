package com.example.b07project;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    public void Add_User(User user, String type, Contract.Presenter presenter){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef  = database.getReference();

        myRef.child(type).addValueEventListener(new ValueEventListener()  {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(user.getUsername()).exists()) {
                    presenter.Create_Failed();
                }else{
                    myRef.child(type).child(user.getUsername()).setValue(user);
                }
                myRef.child(type).removeEventListener(this);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Tag","Error while reading data");
            }
        });
    }
}

