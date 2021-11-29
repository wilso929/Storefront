package com.example.b07project;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyPresenter implements Contract.Presenter{
    private Contract.Model model;
    private Contract.View view;

    public MyPresenter(Contract.Model model, Contract.View view){
        this.model = model;
        this.view = view;
    }

    public void Create_Owner(String store_name){
        String username = view.getUsername();
        String password = view.getPassword();
        if(Authenticate(username,password)){
            Owner owner = new Owner(username, password, store_name);
            model.Add_User(owner,"Owners", this);
        }
    }

    public void Create_Customer(){
        String username = view.getUsername();
        String password = view.getPassword();
        if(Authenticate(username,password)){
            Customer customer = new Customer(username, password);
            model.Add_User(customer,"Customers", this);
        }
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

    public void Vaildlogin(User user, String type){
        view.NextPage(user, type);
    }

    public void Invaildlogin(){
        view.Alert("Invalid", "Username or Password is Incorrect");
    }

    public void Create_Failed() {
        view.Alert("Username Invalid", "Username already exists.");
    }


    public boolean Authenticate(String username, String password){
        Pattern name = Pattern.compile("[a-zA-Z0-9]{7,}");
        Pattern pass = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{7,}$");
        Matcher matchn = name.matcher(username);
        Matcher matchp = pass.matcher(password);
        if(!matchn.matches()){
            view.Alert("Invalid Username", "Usernames must be at least 7 characters long");
            return false;
        }else if(!matchp.matches()) {
            view.Alert("Invalid Password", "Must be: " +
                    "at least 7 characters, " +
                    "include a lowercase, " +
                    "an uppercase, a number, " +
                    "a special character, " +
                    "and not other character types.");
            return false;
        }
        return true;
    }
}
