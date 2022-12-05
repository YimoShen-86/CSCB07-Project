package com.example.loginpage;

import android.util.Patterns;

public class AdminLoginPresenter {
    private FireBaseModel model;
    private AdminLoginPg view;

    public AdminLoginPresenter() {
    }

    public AdminLoginPresenter(FireBaseModel model, AdminLoginPg view){
        this.model =model;
        this.view = view;
    }

    public void login(String email, String password){
        model.login(email, password, (String uid) -> {
            if(uid != null)
                view.goToAdminPage(uid);
            else
                view.displayError();
        });
    }

    public void checkEmail(){
        String email = view.getEmail();
        if(email.isEmpty()) {
            view.displayErrorOnEditTextEmail("Email is required");
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view.displayErrorOnEditTextEmail("Please enter a valid email");
        }
    }

    public void checkPassword(){
        String password = view.getPassword();
        if(password.isEmpty()) {
            view.displayErrorOnEditTextPassword("Password is required");
        }else if(password.length() < 8) {
            view.displayErrorOnEditTextPassword("The length of password should be at least 8 characters");
        }
    }
}
