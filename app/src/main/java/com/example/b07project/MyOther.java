package com.example.b07project;

import java.util.ArrayList;
import java.util.HashSet;

public class MyOther implements Contract.Other{
    private Contract.Model model;

    public MyOther(Contract.Model model) {
        this.model = model;
    }

    public void Update_Owners(SelectStore c) {
        model.Get_Owners(c, this);
    }

    public void Give_Owners(SelectStore c, ArrayList<Owner> updated) {
        if (updated != null) {
            c.allOwners = new ArrayList<Owner>();
            c.allOwners.addAll(updated);
            c.displayAllStores();
        }
    }
}
