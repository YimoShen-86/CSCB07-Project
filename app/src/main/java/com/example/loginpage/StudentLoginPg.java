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
import com.google.firebase.auth.FirebaseUser;

public class StudentLoginPg extends AppCompatActivity implements View.OnClickListener {

    private TextView register, forgotPassword;
    private EditText editTextEmail, editTextPassword;
    private Button logIn;

    private ProgressBar progressBar;

    private StudentLoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login_pg);

        presenter = new StudentLoginPresenter(new FireBaseModel(), this);

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        logIn = (Button) findViewById(R.id.loginbtn);
        logIn.setOnClickListener(this);

        forgotPassword = (TextView) findViewById(R.id.forgotpassword);
        forgotPassword.setOnClickListener(this);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register:
                startActivity(new Intent(this, RegisterUser.class));
                break;
            case R.id.loginbtn:
                studentLogin();
                break;
            case R.id.forgotpassword:
                startActivity(new Intent(this, ForgotPassword.class));
                break;
        }
    }

    private void studentLogin() {
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

    public void goToStudentPage(String uid){
        progressBar.setVisibility(View.GONE);
        Intent intent = new Intent(StudentLoginPg.this, StudentPg.class);
        intent.putExtra("userID", uid);
        startActivity(intent);
    }

    public void displayError(){
        progressBar.setVisibility(View.GONE);
        Toast.makeText(StudentLoginPg.this, "Sorry, unable to login! Pl;ease try again", Toast.LENGTH_LONG).show();
    }
}