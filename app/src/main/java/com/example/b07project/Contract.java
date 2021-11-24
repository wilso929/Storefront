package com.example.b07project;

public interface Contract {
    interface Model{
        void Check_Info(String username, String password, String type, Presenter presenter);
    }

    interface View{
        String getUsername();
        String getPassword();
        void Alert(String title, String msg);
    }

    interface Presenter{
        void Check_Account(String type);
        void Vaildlogin(String username);
        void Invaildlogin();
    }
}