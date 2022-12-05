package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class StudentAddCourse extends AppCompatActivity {
    Button btnStuAddCourse, btnStuCancel;
    EditText TxTTargetCourse;
    FireBaseModel model;
    String userID;
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_add_course);
        btnStuAddCourse = findViewById(R.id.stuUploadCourse);
        btnStuCancel = findViewById(R.id.cancelstuAddCourse);
        TxTTargetCourse = findViewById(R.id.txtCourseCode_stu);
        userID = getIntent().getStringExtra("userID");
        model = new FireBaseModel();
        model.getStudent(userID, (Student student) -> {
            this.student = student;
        });

        btnStuAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (student == null) {
                    Toast.makeText(StudentAddCourse.this, "Error! Can't find the student!", Toast.LENGTH_LONG).show();
                    return;
                }

                String code = TxTTargetCourse.getText().toString().trim().toUpperCase();

                if (student.taken.contains(code)) {
                    TxTTargetCourse.setError("already taken");
                    TxTTargetCourse.requestFocus();
                    return;
                }

                model.getCourses((HashMap<String, Course> courses) -> {
                    if (!courses.containsKey(code)) {
                        TxTTargetCourse.setError(code + " not exist");
                        TxTTargetCourse.requestFocus();
                        return;
                    }

                    if (!student.taken.containsAll(courses.get(code).pre)) {
                        TxTTargetCourse.setError("Missing Prerequisites");
                        TxTTargetCourse.requestFocus();
                        return;
                    }

                    student.taken.add(code);
                    btnStuAddCourse.setEnabled(false);
                    model.saveStudent(userID, student, (Boolean succeed) -> {
                        Toast.makeText(StudentAddCourse.this, succeed ? "Successfully added" : "Failed", Toast.LENGTH_LONG).show();
                        btnStuAddCourse.setEnabled(true);
                        TxTTargetCourse.setText(String.join("\n", student.taken));
                    });
                });
            }
        });

        btnStuCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
}