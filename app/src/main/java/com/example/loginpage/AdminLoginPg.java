package com.example.loginpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminLoginPg extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail, editTextPassword;
    private Button logIn;

    private ProgressBar progressBar;
    private AdminLoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login_pg);

        presenter = new AdminLoginPresenter(new FireBaseModel(), this);

        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        logIn = (Button) findViewById(R.id.loginbtn);
        logIn.setOnClickListener(this);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginbtn:
                presenter.checkEmail();
                presenter.checkPassword();
                progressBar.setVisibility(View.VISIBLE);
                presenter.login(this.getEmail(), this.getPassword());
                break;
            case R.id.forgotpassword:
                startActivity(new Intent(this, ForgotPassword.class));
                break;
        }
    }

/** ORIGINAL CODE(THIS part not APPLYING MVP)
    private void adminLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            return;
        }

        if(password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }else if(password.length() < 8) {
            editTextPassword.setError("The length of password should be at least 8 characters");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        presenter.login(email, password);
    }
 **/

    public String getEmail(){
        return editTextEmail.getText().toString().trim();
    }

    public String getPassword(){
        return editTextPassword.getText().toString().trim();
    }

    public void displayErrorOnEditTextEmail(String s){
        editTextEmail.setError(s);
        editTextEmail.requestFocus();
        return;
    }

    public void displayErrorOnEditTextPassword(String s){
        editTextPassword.setError(s);
        editTextPassword.requestFocus();
        return;
    }

    public void goToAdminPage(String uid){
        progressBar.setVisibility(View.GONE);
        Intent intent = new Intent(AdminLoginPg.this, AdminPg.class);
        intent.putExtra("userID", uid);
        startActivity(intent);
    }

    public void displayError(){
        progressBar.setVisibility(View.GONE);
        Toast.makeText(AdminLoginPg.this, "Sorry, unable to login! Please try again", Toast.LENGTH_LONG).show();
    }
}