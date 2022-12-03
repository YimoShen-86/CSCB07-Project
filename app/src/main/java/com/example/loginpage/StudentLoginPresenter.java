package com.example.loginpage;

public class StudentLoginPresenter {
    private FireBaseModel model;
    private StudentLoginPg view;

    public StudentLoginPresenter(){}

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
}
