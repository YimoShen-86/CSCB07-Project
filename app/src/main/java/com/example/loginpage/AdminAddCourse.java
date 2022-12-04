package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminAddCourse extends AppCompatActivity {

    Button btnAddCourse;
    EditText textCode, textSession;
    EditText textName, textPrerequisite;
    FireBaseModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_course);
        model = new FireBaseModel();

        textCode = findViewById(R.id.txtCourseCode_toAdd);
        textName = findViewById(R.id.txtCourseName_toAdd);
        textSession = findViewById(R.id.txtSession_toAdd);
        textPrerequisite = findViewById(R.id.txtPrereq_toAdd);

        btnAddCourse = (Button) findViewById(R.id.addCourse);
        btnAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = textCode.getText().toString().trim().toUpperCase();
                String name = textName.getText().toString().trim();
                String sessions = textSession.getText().toString().trim().toUpperCase();
                String pre = textPrerequisite.getText().toString().trim().toUpperCase();

                Course newCourse = new Course(code, name, sessions, pre);

                model.saveCourse(newCourse, (Boolean succeed) -> {
                    if (succeed) {
                        Toast.makeText(AdminAddCourse.this, "added", Toast.LENGTH_LONG).show();
                        // TODO: do something else ...

                    } else {
                        Toast.makeText(AdminAddCourse.this, "failed to add", Toast.LENGTH_LONG).show();
                    }
                });

                startActivity(new Intent(AdminAddCourse.this, AdminPg.class));
            }
        });

    }
}