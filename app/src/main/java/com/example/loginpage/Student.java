package com.example.loginpage;

import java.util.ArrayList;
import java.util.List;

public class Student {
    public String fullName, utorid, email;
    public List<String> taken; // taken courses
    public Student(){
        taken = new ArrayList<>();
    }

    public Student(String fullName, String utorid, String email) {
        this();
        this.fullName = fullName;
        this.utorid = utorid;
        this.email = email;
    }


}
