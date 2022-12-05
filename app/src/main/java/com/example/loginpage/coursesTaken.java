package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class coursesTaken extends AppCompatActivity implements View.OnClickListener{
    Button addCourse, viewCourselist;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coursestaken);
        userID =getIntent().getStringExtra("userID");
        addCourse = (Button)findViewById(R.id.stuAddCourse);
        addCourse.setOnClickListener(this);
        viewCourselist = (Button)findViewById(R.id.stuViewCourse);
        viewCourselist.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.stulogOut:
                startActivity(new Intent(coursesTaken.this, MainActivity.class ));
                break;
            case R.id.stuAddCourse:
                intent = new Intent(coursesTaken.this, StudentAddCourse.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
                break;
            case R.id.stuViewCourse:
                intent = new Intent(coursesTaken.this, StudentViewCourseList.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
                break;
        }
    }
}


