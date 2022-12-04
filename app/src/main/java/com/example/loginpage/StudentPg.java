package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StudentPg extends AppCompatActivity implements View.OnClickListener {

    private Button btnGenerateTimeline, btnCourseTaken, btnLogOut;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_pg);
        uid =getIntent().getStringExtra("uid");

        btnCourseTaken = (Button) findViewById(R.id.courseTaken);
        btnCourseTaken.setOnClickListener(this);

        btnGenerateTimeline = (Button) findViewById(R.id.generateTimeline);
        btnGenerateTimeline.setOnClickListener(this);

        btnLogOut = (Button) findViewById(R.id.logout);
        btnLogOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.logout:
                startActivity(new Intent(StudentPg.this, MainActivity.class));
                break;
            case R.id.courseTaken:
                intent = new Intent(StudentPg.this, Coursestaken.class);
                intent.putExtra("uid", uid);
                startActivity(intent);
                break;
            case R.id.generateTimeline:
                intent = new Intent(StudentPg.this, StudentTimeline.class);
                intent.putExtra("uid", uid);
                startActivity(intent);
                break;
        }
    }
}