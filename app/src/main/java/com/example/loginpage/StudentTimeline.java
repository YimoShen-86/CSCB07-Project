package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

public class StudentTimeline extends AppCompatActivity {
    FireBaseModel model;
    String userID;

    EditText txtWantToTake;
    TextView timetable;
    Button btnCreate;

    Session session;
    Student student;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_timeline);
        model = new FireBaseModel();
        userID = getIntent().getStringExtra("userID");

        timetable = findViewById(R.id.takentimetable);
        txtWantToTake = findViewById(R.id.txtWantToTake);
        btnCreate = findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTimetable();
            }
        });

        model.getSession((Session session) -> {
            this.session = session;
        });

        model.getStudent(userID, (Student student) -> {
            this.student = student;
        });

    }

    public void createTimetable() {
        if (session == null) {
            Toast.makeText(this, "Error! Can't find current session.", Toast.LENGTH_LONG).show();
            return;
        }

        if (student == null) {
            Toast.makeText(this, "Error! Can't find the target student.", Toast.LENGTH_LONG).show();
            return;
        }

        String targetCourses[] = txtWantToTake.getText().toString().trim().toUpperCase().split(",");
        LinkedHashMap<String, HashSet<String>> timeline = new LinkedHashMap<>();

        model.getCourses((HashMap<String, Course> courses) -> {
            for (String code: targetCourses) {
                if (!courses.containsKey(code)) {
                    txtWantToTake.setError(code + " does not exist");
                    txtWantToTake.requestFocus();
                    return;
                }
                List<String> taken = new ArrayList<>();
                taken.addAll(student.taken);


                List<String> newTaken = new ArrayList<>();
                List<Course> coursePath = Course.getCoursePath(courses, code);
                List<Course> fallCourses = new ArrayList<Course>();
                List<Course> winterCourses = new ArrayList<Course>();
                List<Course> summerCourses = new ArrayList<Course>();
                for (Course cur : coursePath) {
                    for (String offerSes : cur.session) {
                        if (offerSes == null) continue;
                        if (offerSes.equals("FALL")) {
                            fallCourses.add(cur);
                        } else {
                            if (offerSes.equals("WINTER")) {
                                winterCourses.add(cur);
                            } else {
                                if (offerSes.equals("SUMMER")) {
                                    summerCourses.add(cur);
                                }
                            }
                        }
                    }
                }

                Session curSession = session;

                while (!coursePath.isEmpty()) {
                    List<Course> removeCourseList = new ArrayList<Course>();
                    List<Course> currOfferCourses = new ArrayList<Course>();
                    if (curSession.semester.toString().equals("FALL")) {
                        currOfferCourses = fallCourses;
                    }
                    if (curSession.semester.toString().equals("WINTER")) {
                        currOfferCourses = winterCourses;
                    }
                    if (curSession.semester.toString().equals("SUMMER")) {
                        currOfferCourses = summerCourses;
                    }

                    for (Course currCourse : currOfferCourses) {
                        if (taken.contains(currCourse.code)) {
                            removeCourseList.add(currCourse);
                            continue;
                        }
                        if (taken.containsAll(currCourse.pre) && currOfferCourses.contains(currCourse)) {
                            newTaken.add(currCourse.code);
                        }
                    }

                    String key = curSession.toString();
                    if (!timeline.containsKey(key)){
                        timeline.put(key, new HashSet<>());
                    }
                    timeline.get(key).addAll(newTaken);
                    curSession = Session.nextSession(curSession);

                    for(Course c : removeCourseList){
                        coursePath.remove(c);
                    }

                    for (String takenCode : newTaken){
                        Course courseToDelete = courses.get(takenCode);
                        coursePath.remove(courseToDelete);
                    }
                    taken.addAll(newTaken);
                    newTaken.clear();
                }

                String tmp = "";
                for (String key: timeline.keySet()) {
                    tmp = tmp + key + "  " + String.join(", ", timeline.get(key)) + "\n";
                }
                timetable.setText(String.join("\n", tmp));
                fallCourses.clear();
                winterCourses.clear();
                summerCourses.clear();
            }
        });
    }
}