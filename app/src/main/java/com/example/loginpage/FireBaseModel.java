package com.example.loginpage;

import androidx.annotation.NonNull;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

public class FireBaseModel {
    private FirebaseAuth mAuth;
    private DatabaseReference studentRef;
    private DatabaseReference adminRef;
    private DatabaseReference courseRef;
    private DatabaseReference sessionRef;

    public FireBaseModel(){
        mAuth = FirebaseAuth.getInstance();
        adminRef = FirebaseDatabase.getInstance().getReference("Admin");
        studentRef = FirebaseDatabase.getInstance().getReference("Students");
        courseRef = FirebaseDatabase.getInstance().getReference("Courses");
        sessionRef = FirebaseDatabase.getInstance().getReference("Session");
    }

    public void register(String email, String password, Consumer<String> callback){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    callback.accept(mAuth.getUid());
                }
                else {
                    callback.accept(null);
                }
            }
        });
    }

    public void login(String email, String password, Consumer<String> callback) {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful() && !mAuth.getUid().equals("VwrGm5pVYcWMPmvbb4RlPks7ajp1")){
                    callback.accept(mAuth.getUid());
                }
                else {
                    callback.accept(null);
                }
            }
        });
    }

    public void adminLogin(String email, String password, Consumer<String> callback) {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful() && mAuth.getUid().equals("VwrGm5pVYcWMPmvbb4RlPks7ajp1")){
                    callback.accept(mAuth.getUid());
                }
                else {
                    callback.accept(null);
                }
            }
        });
    }
    public void getCourses(Consumer<HashMap<String, Course>> callback){
        courseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, Course> courses = new HashMap<>();
                for(DataSnapshot courseSnapShot: snapshot.getChildren()){
                    Course course = courseSnapShot.getValue(Course.class);
                    courses.put(course.code, course);
                }
                callback.accept(courses);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void saveCourse(Course course, Consumer<Boolean> callback){
        courseRef.child(course.code).setValue(course).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                callback.accept(task.isSuccessful());
            }
        });
    }

    public void removeCourse(String code, Consumer<Boolean> callback){
        courseRef.child(code).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                callback.accept(task.isSuccessful());
            }
        });
    }

    public void getTakenCourses(String userID,Consumer<ArrayList<String>> callback){
        studentRef.child(userID).child("taken").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> takenCourses = new ArrayList<String>();
                for(DataSnapshot takenCourseSnapShot: snapshot.getChildren()){
                    String takenCourse = takenCourseSnapShot.getValue(String.class);
                    takenCourses.add(takenCourse);
                }
                callback.accept(takenCourses);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void saveTakenCourse(String userID, String index, String courseCode, Consumer<Boolean> callback){
        studentRef.child(userID).child("taken").child(index).setValue(courseCode).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                callback.accept(task.isSuccessful());
            }
        });
    }

    public void removeTakenCourse(String userID, String index,Consumer<Boolean> callback){
        studentRef.child(userID).child("taken").child(index).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                callback.accept(task.isSuccessful());
            }
        });
    }

    public void getStudent(String userID, Consumer<Student> callback){
        studentRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Student student = snapshot.getValue(Student.class);
                callback.accept(student);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void saveStudent(String uid, Student student, Consumer<Boolean> callback){
        studentRef.child(uid).setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                callback.accept(task.isSuccessful());
            }
        });
    }

    public void getSession(Consumer<Session> callback){
        sessionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Session session = snapshot.getValue(Session.class);
                callback.accept(session);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}
