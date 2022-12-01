package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView admin;
    private TextView student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        admin = (TextView) findViewById(R.id.btnAdmin);
        admin.setOnClickListener(this);

        student = (TextView) findViewById(R.id.btnStudent);
        student.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnStudent:
                startActivity(new Intent(this, StudentLoginPg.class));
                break;
            case R.id.btnAdmin:
                startActivity(new Intent(this, AdminLoginPg.class));
                break;
        }
    }
}