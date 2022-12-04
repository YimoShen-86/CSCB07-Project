package com.example.loginpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener{

    private TextView signUp, register, returnBtn;
    private EditText editTextFullName, editTextStudentNumber, editTextEmail, editTextPassword;
    private ProgressBar progressBar;

    private FireBaseModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        model = new FireBaseModel();

        signUp = (TextView) findViewById(R.id.signUp);
        signUp.setOnClickListener(this);

        register = (TextView) findViewById(R.id.btnresgisterUser);
        register.setOnClickListener(this);
        returnBtn = (TextView) findViewById(R.id.btnReturn);
        returnBtn.setOnClickListener(this);

        editTextFullName = (EditText) findViewById(R.id.fullName);
        editTextStudentNumber = (EditText) findViewById(R.id.studentNumber);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnReturn:
                startActivity(new Intent(this, StudentLoginPg.class));
                break;
            case R.id.btnresgisterUser:
                registerStudent();
                break;
        }
    }

    private void registerStudent() {
        String fullName = editTextFullName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String studentNumber = editTextStudentNumber.getText().toString().trim();

        if(fullName.isEmpty()) {
            editTextFullName.setError("Full Name is required");
            editTextFullName.requestFocus();
            return;
        }

        if(studentNumber.isEmpty()) {
            editTextStudentNumber.setError("Student Number is required");
            editTextStudentNumber.requestFocus();
            return;
        }else if(!Patterns.PHONE.matcher(studentNumber).matches()) {
            editTextStudentNumber.setError("Please enter a valid Student Number!!!");
            editTextStudentNumber.requestFocus();
            return;
        }

        if(email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email!!!");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }else if(password.length() < 8) {
            editTextPassword.setError("The length of password should be at least 8 characters!");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        model.register(email, password, (String userID) -> {
            if (userID != null) {
                Student student = new Student(fullName, studentNumber, email);
                model.saveStudent(userID, student, (Boolean succeed) -> {
                    Toast.makeText(RegisterUser.this,
                            succeed ? "Student has been registered successfully" : "Registration failed! Try again",
                            Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                });
            } else {
                Toast.makeText(RegisterUser.this, "Registration failed! Try again!", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}