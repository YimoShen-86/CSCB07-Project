package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudentViewCourseList extends AppCompatActivity {

    FireBaseModel model;
    ListView takenCourseListView;
    private Button btnback;
    String userID;
    Student student;
    List<String> taken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_course_list);
        userID = getIntent().getStringExtra("userID");
        model = new FireBaseModel();
        model.getStudent(userID, (Student student) -> {
            this.student = student;
        });

        takenCourseListView = findViewById(R.id.takenCoursesListView);
        takenCourseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String courseCode = taken.get(i);
                Intent intent = new Intent(StudentViewCourseList.this, CourseDetail.class);
                intent.putExtra("courseCode", courseCode);
                startActivity(intent);
            }
        });

        btnback = (Button) findViewById(R.id.backViewCourseList);

        model.getTakenCourses(userID,(ArrayList<String> takenCourses) -> {
            taken = takenCourses;
            ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, takenCourses);
            takenCourseListView.setAdapter(adapter);
        });
    }

    public void BackToStudentPg(View view){
        switch(view.getId()){
            case R.id.backViewCourseList:
                startActivity(new Intent(StudentViewCourseList.this, StudentPg.class));
                break;
        }
    }
}