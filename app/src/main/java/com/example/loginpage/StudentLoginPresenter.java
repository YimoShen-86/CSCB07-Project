package com.example.loginpage;

import androidx.core.util.PatternsCompat;

public class StudentLoginPresenter {
    private FireBaseModel model;
    private StudentLoginPg view;

    public StudentLoginPresenter(FireBaseModel model, StudentLoginPg view){
        this.model= model;
        this.view = view;
    }

    public void login(String email, String password){
        model.login(email, password, (String uid) -> {
           if(uid != null)
               view.goToStudentPage(uid);
           else
               view.displayError();
        });
    }

    public boolean checkEmail(){
        String email = view.getEmail();
        if(email.isEmpty()) {
            view.displayErrorOnEditTextEmail("Email is required");
            return true;
        }else if(!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) {
            view.displayErrorOnEditTextEmail("Please enter a valid email");
            return true;
        }
        return false;
    }

    public boolean checkPassword(){
        String password = view.getPassword();
        if(password != null && password.isEmpty()) {
            view.displayErrorOnEditTextPassword("Password is required");
            return true;
        }else if(password != null && password.length() < 8) {
            view.displayErrorOnEditTextPassword("The length of password should be at least 8 characters");
            return true;
        }
        return false;
    }
}
