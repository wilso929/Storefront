package com.example.b07project;

public class MyPresenter implements Contract.Presenter{
    private Contract.Model model;
    private Contract.View view;

    public MyPresenter(Contract.Model model, Contract.View view){
        this.model = model;
        this.view = view;
    }

    public void Check_Account(String type) {
        String username = view.getUsername();
        String password = view.getPassword();


        if(username.equals("") || password.equals("")){
            view.Alert("Invalid", "Username or Password Left Blank");
        }else{
            model.Check_Info(username, password, type, this);
        }

    }

    public void Vaildlogin(String username){
        view.Alert("Yay", "you did it");

    }

    public void Invaildlogin(){
        view.Alert("Invalid", "Username or Password is Incorrect");
    }
}
