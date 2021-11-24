package com.example.b07project;

public interface Contract {
    interface Model{
        void Check_Info(String username, String password, String type, Presenter presenter);
        void Add_User(User user, String type, Presenter presenter);
    }

    interface View{
        String getUsername();
        String getPassword();
        void Alert(String title, String msg);
        void NextPage(String username, String type);
    }

    interface Presenter{
        void Check_Account(String type);
        void Vaildlogin(String username, String type);
        void Invaildlogin();
        void Create_Failed();
        void Create_Customer();
        void Create_Owner(String store_name);
    }
}