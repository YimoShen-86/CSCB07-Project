package com.example.loginpage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Student implements Serializable {
    public String fullName, studentNumber, email;
    public List<String> taken; // taken courses
    public Student(){
        taken = new ArrayList<>();
    }

    public Student(String fullName, String studentNumber, String email) {
        this();
        this.fullName = fullName;
        this.studentNumber = studentNumber;
        this.email = email;
    }


}
