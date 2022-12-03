package com.example.loginpage;

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
}
