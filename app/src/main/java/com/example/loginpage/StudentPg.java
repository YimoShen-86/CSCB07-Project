package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StudentPg extends AppCompatActivity implements View.OnClickListener {

    private Button btnGenerateTimeline, btnCourseTaken, btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_pg);

        btnCourseTaken = (Button) findViewById(R.id.courseTaken);
        btnCourseTaken.setOnClickListener(this);

        btnGenerateTimeline = (Button) findViewById(R.id.generateTimeline);
        btnGenerateTimeline.setOnClickListener(this);

        btnLogOut = (Button) findViewById(R.id.logout);
        btnLogOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.logout:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.courseTaken:
<<<<<<< Updated upstream
                startActivity(new Intent(this, Coursestaken.class));
=======
                intent = new Intent(StudentPg.this, coursesTaken.class);
                intent.putExtra("uid", uid);
                startActivity(intent);
>>>>>>> Stashed changes
                break;
            case R.id.generateTimeline:
                //enter timeline page
                //startActivity(new Intent(this, ...);
        }
    }
}