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

import java.util.HashMap;
import java.util.function.Consumer;

public class FireBaseModel {
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    private DatabaseReference courseRef;
    private DatabaseReference sessionRef;

    public FireBaseModel(){
        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference("Students");
        userRef = FirebaseDatabase.getInstance().getReference("Courses");
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
                if(task.isSuccessful()){
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
        userRef.child(course.code).setValue(course).addOnCompleteListener(new OnCompleteListener<Void>() {
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

    public void getStudent(String uid, Consumer<Student> callback){
        userRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
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
        userRef.child(uid).setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
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
