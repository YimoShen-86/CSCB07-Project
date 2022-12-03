package com.example.loginpage;

import java.io.Serializable;

public class Session implements Serializable {
    public int year;
    public String semester;

    public Session(){}

    public Session(int year, String semester){
        this.year =year;
        this. semester = semester;
    }

    public static Session nextSession(Session currentSession){
        String[] seasons = new String[]{"WINTER", "SUMMER", "FALL"};
        for(int i = 0; i < seasons.length; i++){
            if(currentSession.semester.equals(seasons[i])){
                return new Session(currentSession.year + (Integer)(i+1)/seasons.length, seasons[(i+1)% seasons.length]);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return year + " " + semester;
    }
}
