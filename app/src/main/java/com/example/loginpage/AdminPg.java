package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminPg extends AppCompatActivity implements View.OnClickListener{

    private Button btnAddCourse;
    private Button btnViewCourseList;
    private Button btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pg);

        btnAddCourse = (Button) findViewById(R.id.addCourse);
        btnAddCourse.setOnClickListener(this);

        btnViewCourseList = (Button) findViewById(R.id.viewCourseList);
        btnViewCourseList.setOnClickListener(this);

        btnLogOut = (Button) findViewById(R.id.logOut);
        btnLogOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.logOut:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.addCourse:

        }
    }
}