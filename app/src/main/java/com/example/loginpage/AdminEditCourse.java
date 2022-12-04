package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class AdminEditCourse extends AppCompatActivity {

    Button btnUpdateCourse, btnDeleteCourse;
    EditText textCode, textSession;
    EditText textName, textPrerequisite;
    FireBaseModel model;

    Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_course);
        model = new FireBaseModel();

        textCode = findViewById(R.id.txtCourseCode_toEdit);
        textName = findViewById(R.id.txtCourseName_toEdit);
        textSession = findViewById(R.id.txtSession_toEdit);
        textPrerequisite = findViewById(R.id.txtPrereq_toEdit);

        course = (Course) getIntent().getSerializableExtra("course");

        if (course != null) {
            textCode.setText(course.code);
            textName.setText(course.name);
            textSession.setText(String.join(",", course.session));
            textPrerequisite.setText(String.join(",", course.pre));
        }


        btnUpdateCourse = findViewById(R.id.updateCourse);
        btnUpdateCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = textCode.getText().toString().trim().toUpperCase();
                String name = textName.getText().toString().trim();
                String sessions = textSession.getText().toString().trim().toUpperCase();
                String pre = textPrerequisite.getText().toString().trim().toUpperCase();

                Course newCourse = new Course(code, name, sessions, pre);

                // save this course object to firebase
                model.saveCourse(newCourse, (Boolean succeed) -> {
                    if (succeed) {
//                        System.out.println(code + " " + name + " " + sessions + " " + pre);
                        Toast.makeText(AdminEditCourse.this, code + " " + name + " " + sessions + " " + pre,Toast.LENGTH_LONG).show();
                        Toast.makeText(AdminEditCourse.this, "updated", Toast.LENGTH_LONG).show();
                        // TODO: do something else ...
                        startActivity(new Intent(AdminEditCourse.this, AdminCourseList.class));
                    } else {
                        Toast.makeText(AdminEditCourse.this, "failed to update", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        btnDeleteCourse = findViewById(R.id.deleteCourse);
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
                model.removeCourse(code, (Boolean succeed) -> {
                    if (succeed) {

                        // update all the courses that contains this as pre
                        model.getCourses((HashMap<String, Course> courses) -> {

                            for (Course course : courses.values()) {
                                if (course.pre.contains(code)) {
                                    course.pre.remove(code);
                                    model.saveCourse(course, (Boolean succeed2) -> {});
                                }
                            }
                        });

                        Toast.makeText(AdminEditCourse.this, "deleted", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(AdminEditCourse.this, AdminCourseList.class));
                    } else {
                        Toast.makeText(AdminEditCourse.this, "failed to delete", Toast.LENGTH_LONG).show();
                    }
                });



            }
        });
    }
}