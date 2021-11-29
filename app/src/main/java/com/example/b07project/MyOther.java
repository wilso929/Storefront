package com.example.b07project;

import java.util.HashSet;

public class MyOther implements Contract.Other{
    private Contract.Model model;

    public MyOther(Contract.Model model) {
        this.model = model;
    }

    public void Update_Owners(DisplayCustomerActivity c) {
        model.Get_Owners(c, this);
    }

    public void Give_Owners(DisplayCustomerActivity c, HashSet<Owner> updated) {
        if (updated != null) {
            c.allOwners = new HashSet<Owner>();
            c.allOwners.addAll(updated);
        }
    }
}
