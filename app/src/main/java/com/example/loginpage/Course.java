package com.example.loginpage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

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

    public static List<Course> getCoursePath(HashMap<String, Course> courses, String code) {
        if (!courses.containsKey(code)) {
            return null;
        }

        List<Course> path = new ArrayList<Course>();
        Stack<String> stk = new Stack<String>();
        Stack<Course> neededCourse = new Stack<Course>();

        stk.push(code);
        String courseCode;

        while (!stk.empty()) {
            String cur = stk.pop();
            Course curCourse = courses.get(cur);
            if(neededCourse.search(curCourse) > -1){
                continue;
            }else{
                neededCourse.push(curCourse);
            }

            for (String next : curCourse.pre) {
                if(stk.search(next) > -1) continue;
                else stk.push(next);
            }
        }
        while(!neededCourse.empty()){
            Course curCourse = neededCourse.pop();
            if(!path.contains(curCourse)) path.add(curCourse);
        }
        return path;
    }
}
