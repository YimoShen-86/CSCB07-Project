package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class CourseDetail extends AppCompatActivity {

    Button btnUpdateCourse, btnDeleteCourse;
    EditText textCode;
    FireBaseModel model;
    String userID, index, courseCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        model = new FireBaseModel();

        textCode = findViewById(R.id.txtTakenCourse_toEdit);

        index = getIntent().getStringExtra("index");
        userID = getIntent().getStringExtra("userID");
        courseCode = getIntent().getStringExtra("courseCode");

        if (courseCode != null) {
            textCode.setText(courseCode);
        }

        btnUpdateCourse = findViewById(R.id.updateTakenCourse);
        btnUpdateCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newCourseCode = textCode.getText().toString().trim().toUpperCase();

                // save this course object to firebase
                model.saveTakenCourse(userID, index, newCourseCode, (Boolean succeed) -> {
                    if (succeed) {
                        Toast.makeText(CourseDetail.this, index + " " + newCourseCode,Toast.LENGTH_LONG).show();
                        Toast.makeText(CourseDetail.this, "updated", Toast.LENGTH_LONG).show();

                        startActivity(new Intent(CourseDetail.this, StudentViewCourseList.class));
                    } else {
                        Toast.makeText(CourseDetail.this, "failed to update", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        btnDeleteCourse = findViewById(R.id.deleteTakenCourse);
        btnDeleteCourse.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String code = textCode.getText().toString().trim().toUpperCase();
                if (code.isEmpty()) {
                    textCode.setError("Course Code is required");
                    textCode.requestFocus();
                    return;
                }

                // save this course object to firebase
                model.removeTakenCourse(userID, index, (Boolean succeed) -> {
                    if (succeed) {
                        Toast.makeText(CourseDetail.this, "deleted", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(CourseDetail.this, StudentViewCourseList.class));
                    } else {
                        Toast.makeText(CourseDetail.this, "failed to delete", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }

    public void LogOutFromStudentEditCourse(View view){
        switch(view.getId()){
            case R.id.logOutFromEditTakenCoursePg:Course:
            startActivity(new Intent(CourseDetail.this, MainActivity.class));
                break;
        }
    }
}