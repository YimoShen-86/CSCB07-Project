package com.example.loginpage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Course implements Serializable {
    public String code; // course code (id)
    public String name; // course name
    public List<String> pre; // prerequisite
    public List<String> session; // offering session

    public Course(){
        this.pre = new ArrayList<String>();
        this.session = new ArrayList<String>();
    }

    public Course(String courseCode, String courseName, String preRequisite, String offerSession) {
        this();
        this.code = courseCode;
        this.name = courseName;
        if (!preRequisite.isEmpty()) {
            this.pre.addAll(Arrays.asList(preRequisite.toUpperCase().split(",")));
        }
        if (!offerSession.isEmpty()) {
            this.session.addAll(Arrays.asList(offerSession.toUpperCase().split(",")));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return (this.code).equals(course.code);
    }
}
