package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdminCourseList extends AppCompatActivity {

    FireBaseModel model;
    ListView courseListView;
    List<Course> courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_course_list);

        courseListView = findViewById(R.id.courseListView);
        courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Course course = courses.get(i);
                Intent intent = new Intent(AdminCourseList.this, AdminEditCourse.class);
                intent.putExtra("course", course);
                startActivity(intent);
            }
        });

        model = new FireBaseModel();
        model.getCourses((HashMap<String, Course> coursesMap) -> {
            List<String> courseCodes = new ArrayList<>();
            courseCodes.addAll(coursesMap.keySet());

            courses = new ArrayList<>();
            courses.addAll(coursesMap.values());

            ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, courseCodes);
            courseListView.setAdapter(adapter);
        });
    }
}