package com.example.a229zzg.nusanswers;

import java.util.ArrayList;

public class UserInfo {
    private String userId;
    private String username;
    private String program;
    private ArrayList<String> currentEnrolledModules;
    //private ArrayList<String> completedModules;

    public UserInfo(){
        this.userId = "";
        this.username = "";
        this.program = "";
        this.currentEnrolledModules = new ArrayList<>();
        //this.completedModules = new ArrayList<>();
    }

    public UserInfo(String userId, String username) {
        this.userId = userId;
        this.username = username;
        this.program = "";
        this.currentEnrolledModules = new ArrayList<>();
        //this.completedModules = new ArrayList<>();
    }
    public String getUsername() {
        return this.username;
    }
    public void setProgram(String program) {
        this.program = program;
    }

    public String getProgram() {
        return this.program;
    }

    public void setCurrentEnrolledModules(ArrayList<String> currentEnrolledModules) {
        this.currentEnrolledModules = currentEnrolledModules;
    }

    public ArrayList<String> getCurrentEnrolledModules() {
        return this.currentEnrolledModules;
    }

}


