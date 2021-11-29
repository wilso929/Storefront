package com.example.b07project;

import java.util.HashSet;

public interface Contract {
    interface Model{
        void Check_Info(String username, String password, String type, Presenter presenter);
        void Add_User(User user, String type, Presenter presenter);
        void Get_Owners(DisplayCustomerActivity c, Other other);
    }

    interface View{
        String getUsername();
        String getPassword();
        void Alert(String title, String msg);
        void NextPage(User user, String type);
    }

    interface Presenter{
        void Check_Account(String type);
        void Vaildlogin(User user, String type);
        void Invaildlogin();
        void Create_Failed();
        void Create_Customer();
        void Create_Owner(String store_name);
    }

    interface Other{
        void Update_Owners(DisplayCustomerActivity c);
        void Give_Owners(DisplayCustomerActivity c, HashSet<Owner> updated);
    }
}