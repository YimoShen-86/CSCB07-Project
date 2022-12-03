package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Coursestaken extends AppCompatActivity {
    private Button btnInsert, btnView;
    private EditText course;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("Taken Courses");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coursestaken);

        btnInsert = findViewById(R.id.btninsert);
        btnView = findViewById(R.id.btnview);
        course = findViewById(R.id.coursename);


        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Coursestaken.this , coursecart.class));

            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String coursename = course.getText().toString();
                HashMap<String, String> userMap = new HashMap<>();

                userMap.put("coursecode", coursename);

                root.push().setValue(userMap);
            }
        });
    }
}