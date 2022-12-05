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
    ListView courseListView;
    List<Course> courses;
    private Button btnback;
    String userID;
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_course_list);
        userID = getIntent().getStringExtra("userID");
        model = new FireBaseModel();
        model.getStudent(userID, (Student student) -> {
            this.student = student;
        });

        courseListView = findViewById(R.id.courseListView);
        courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Course course = courses.get(i);
                Intent intent = new Intent(StudentViewCourseList.this, CourseDetail.class);
                intent.putExtra("course", course);
                startActivity(intent);
            }
        });

        btnback = (Button) findViewById(R.id.backViewCourseList);


        model.getCourses((HashMap<String, Course> coursesMap) -> {
            List<String> courseCodes = new ArrayList<>();
            courseCodes.addAll(coursesMap.keySet());

            courses = new ArrayList<>();
            courses.addAll(coursesMap.values());

            ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, courseCodes);
            courseListView.setAdapter(adapter);
        });
    }

    public void LogOutFromCourseList(View view){
        switch(view.getId()){
            case R.id.logOutFromCourseList:
                startActivity(new Intent(StudentViewCourseList.this, MainActivity.class));
                break;
        }
    }
}